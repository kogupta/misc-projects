package org.kogu.zkIntro;

import org.apache.zookeeper.*;
import org.apache.zookeeper.AsyncCallback.StatCallback;
import org.apache.zookeeper.AsyncCallback.StringCallback;
import org.apache.zookeeper.KeeperException.Code;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.zookeeper.CreateMode.EPHEMERAL_SEQUENTIAL;
import static org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE;
import static org.kogu.zkIntro.Worker.Status.IDLE;

public final class Worker implements Watcher {
    private static final Logger LOG = LoggerFactory.getLogger(Worker.class);
    private static final AtomicInteger seq = new AtomicInteger();

    private final String zkPath;
    private final String hostPort;
    private final int id;
    private ZooKeeper zk;
    private Status status;

    public Worker(String hostPort) {
        this.hostPort = hostPort;
        this.id = seq.getAndIncrement();
        this.status = IDLE;
        zkPath = "/workers/worker-" + id;
    }

    public void startZK() throws IOException {
        int timeout = (int) TimeUnit.SECONDS.toMillis(15);
        zk = new ZooKeeper(hostPort, timeout, this);
    }

    @Override
    public void process(WatchedEvent event) {
        LOG.info("{}, {}", event.toString(), hostPort);
    }

    private final StringCallback createWorkerCb = (rc, path, ctx, name) -> {
        int id = (int) ctx;
        Code code = Code.get(rc);
        switch (code) {
            case OK:
                LOG.info("Registered successfully: {}", id);
                break;
            case CONNECTIONLOSS:
                register();
                break;
            case NODEEXISTS:
                LOG.info("Already registered: {}", id);
                break;
            default:
                LOG.error("Something went wrong:", KeeperException.create(code));
                break;
        }
    };

    public void register() {
        zk.create(zkPath,
                  IDLE.data,
                  OPEN_ACL_UNSAFE,
                  EPHEMERAL_SEQUENTIAL,
                  createWorkerCb,
                  id);
    }

    public void setStatus(Status status) {
        this.status = status;
        updateStatus(status);
    }

    private final StatCallback statusUpdateCb = (rc, path, ctx, stat) -> {
        switch (Code.get(rc)) {
            case OK:
                LOG.info("Successfully updated state [{}] to: {}", path, ctx);
                break;
            case CONNECTIONLOSS:
                updateStatus((Status) ctx);
                break;
            default:
                LOG.info(String.format("While updating [%s] to: %s, got return code: %s",
                                       path, ctx, Code.get(rc)));
                break;
        }
    };

    private synchronized void updateStatus(Status status) {
        if (status == this.status) {
            zk.setData(zkPath, status.data, -1, statusUpdateCb, status);
        }
    }

    public enum Status {
        IDLE("Idle"),
        WORKING("Working"),
        ;

        public final byte[] data;

        Status(String name) {
            data = name.getBytes(UTF_8);
        }
    }
}
