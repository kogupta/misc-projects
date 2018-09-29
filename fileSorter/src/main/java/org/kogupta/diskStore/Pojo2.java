package org.kogupta.diskStore;

import java.nio.ByteBuffer;

public final class Pojo2 {
    private String uuid;
    private String tenant;
    private String secondaryKey;
    private long timestamp;
    private String time;

    public String getUuid() { return uuid; }

    public void setUuid(String uuid) { this.uuid = uuid; }

    public String getTenant() { return tenant; }

    public void setTenant(String tenant) { this.tenant = tenant; }

    public String getSecondaryKey() { return secondaryKey; }

    public void setSecondaryKey(String secondaryKey) { this.secondaryKey = secondaryKey; }

    public long getTimestamp() { return timestamp; }

    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public String getTime() { return time; }

    public void setTime(String time) { this.time = time; }

    public void writeToBB(ByteBuffer buffer) {
        // TODO
    }
}
