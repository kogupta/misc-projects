package org.kogu.zkIntro;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.NoNodeException;
import org.apache.zookeeper.KeeperException.NodeExistsException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.apache.zookeeper.CreateMode.EPHEMERAL;
import static org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE;

public final class Master implements Watcher {
    private static final int timeout = (int) TimeUnit.SECONDS.toMillis(10);
    private static final Random r = new Random();

    private ZooKeeper zk;
    private boolean isLeader;
    private final String hostPort;
    private final byte[] serverId;

    public Master(String hostPort) {
        this.hostPort = hostPort;
        String serverId = Long.toString(r.nextLong());
        this.serverId = serverId.getBytes(StandardCharsets.UTF_8);
    }

    public void startZK() throws IOException {
        this.zk = new ZooKeeper(hostPort, timeout, this);
    }

    public void stopZK() throws InterruptedException {
        zk.close();
    }

    private boolean checkMaster() throws InterruptedException {
        while (true) {
            Stat stat = new Stat();
            try {
                byte[] data = zk.getData("/master", false, stat);
                isLeader = Arrays.equals(serverId, data);
                return true;
            } catch (NoNodeException e) {
                return false; // retry
            } catch (KeeperException ignored) {}
        }
    }

    public void runForMaster(int id) throws InterruptedException {
        while (true) {
            try {
                zk.create("master", serverId, OPEN_ACL_UNSAFE, EPHEMERAL);
                isLeader = true;
                break;
            } catch (NodeExistsException e) {
                isLeader = false;
                break;
            } catch (KeeperException ignored) {}

            // check if master node is created; else retry
            // it may be the case that - this process is master, but response was lost
            if (checkMaster()) break;
        }
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println(event);
    }
}
