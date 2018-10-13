package org.kogu.zkIntro;

import org.apache.zookeeper.AsyncCallback.DataCallback;
import org.apache.zookeeper.AsyncCallback.StringCallback;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.apache.zookeeper.CreateMode.EPHEMERAL;
import static org.apache.zookeeper.CreateMode.PERSISTENT;
import static org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE;

public final class AsyncMaster implements Watcher, Closeable {
    private static final Logger LOG = LoggerFactory.getLogger(AsyncMaster.class);
    private static final Random r = new Random();

    private ZooKeeper zk;
    private boolean isLeader;
    private final String hostPort;
    private final byte[] serverId;

    public AsyncMaster(String hostPort) {
        this.hostPort = hostPort;
        String serverId = Long.toString(r.nextLong());
        this.serverId = serverId.getBytes(StandardCharsets.UTF_8);
    }

    public void startZK() throws IOException {
        int timeout = (int) TimeUnit.SECONDS.toMillis(10);
        this.zk = new ZooKeeper(hostPort, timeout, this);
    }

    public void stopZK() throws InterruptedException {
        zk.close();
    }

    private final DataCallback masterCheckCb = (rc, path, ctx, data, stat) -> {
        byte[] expectedServerId = (byte[]) ctx;
        switch (Code.get(rc)) {
            case OK:
                isLeader = Arrays.equals(expectedServerId, data);
                break;
            case CONNECTIONLOSS:
                checkMaster();
                break;
            case NONODE:
                runForMaster();
                break;
            default:
                break;
        }
    };

    private void checkMaster() {
        zk.getData("/master", false, masterCheckCb, serverId);
    }

    private final StringCallback masterCreateCb = (rc, path, ctx, name) -> {
        switch (Code.get(rc)) {
            case CONNECTIONLOSS:
                checkMaster();
                return;
            case OK:
                isLeader = true;
                break;
            default:
                isLeader = false;
                break;
        }

        String s = isLeader ? "" : "not ";
        System.out.println("I am " + s + "the leader.");
    };

    public void runForMaster() {
        zk.create("/master", serverId, OPEN_ACL_UNSAFE, EPHEMERAL, masterCreateCb, null);
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println(event);
    }

    @Override
    public void close() {
        if (zk == null) { return;}
        try {
            zk.close();
        } catch (InterruptedException e) {
            LOG.error("Interrupted while closing ZooKeeper session.", e);
        }
    }

    public void bootstrap() {
        byte[] dummy = new byte[0];
        createParent("/workers", dummy);
        createParent("/assign", dummy);
        createParent("/tasks", dummy);
        createParent("/status", dummy);
    }

    private final StringCallback createParentCb = (rc, path, ctx, name) -> {
        Code code = Code.get(rc);
        switch (code) {
            case OK:
                LOG.info("Parent at path: {} created", path);
                break;
            case CONNECTIONLOSS:
                // dont know what happened
                // retry
                createParent(path, (byte[]) ctx);
                break;
            case NODEEXISTS:
                // on retry we get it was created in first attempt - do nothing
                LOG.info("Parent at path: {} already created", path);
                break;
            default:
                LOG.error("Something went wrong:", KeeperException.create(code));
                break;
        }
    };

    private void createParent(String path, byte[] data) {
        zk.create(path, data, OPEN_ACL_UNSAFE, PERSISTENT, createParentCb, data);
    }
}
