package org.kogupta.diskStore;

import java.nio.ByteBuffer;

import static org.kogupta.diskStore.utils.Functions.getString;
import static org.kogupta.diskStore.utils.Functions.putString;

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

    public ByteBuffer writeToBB(ByteBuffer buffer) {
        putString(buffer, uuid);
        buffer.putLong(timestamp);
        putString(buffer, tenant);
        putString(buffer, secondaryKey);
        putString(buffer, time);

        return buffer;
    }

    public static Pojo2 readFromBB(ByteBuffer buffer) {
        Pojo2 result = new Pojo2();
        result.setUuid(getString(buffer));
        result.setTimestamp(buffer.getLong());
        result.setTenant(getString(buffer));
        result.setSecondaryKey(getString(buffer));
        result.setTime(getString(buffer));

        return result;
    }
}
