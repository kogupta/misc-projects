package com.oracle.emcsas.fileSorter;

import com.oracle.emcsas.utils.Functions;

import java.nio.ByteBuffer;

import static com.oracle.emcsas.utils.Functions.require;

public class Pojo {
    private int id;
    private String tenantId;
    private long timestamp;
    private String payload;

    public int getId() { return id; }

    public void setId(int id) { this.id = id;}

    public String getTenantId() { return tenantId; }

    public void setTenantId(String tenantId) { this.tenantId = tenantId;}

    public long getTimestamp() { return timestamp; }

    public void setTimestamp(long timestamp) { this.timestamp = timestamp;}

    public String getPayload() { return payload; }

    public void setPayload(String payload) { this.payload = payload;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pojo pojo = (Pojo) o;
        return id == pojo.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return "Pojo{" +
                "id=" + id +
                ", tenantId='" + tenantId + '\'' +
                ", timestamp=" + timestamp +
                ", payload='" + payload + '\'' +
                '}';
    }

    public byte[] toByteArray() {
        ByteBuffer buffer = ByteBuffer.allocate(64);
        buffer.putInt(id);
        buffer.putLong(timestamp);
        Functions.putString(buffer, tenantId);
        Functions.putString(buffer, payload);

        buffer.flip();

        byte[] bb = new byte[buffer.limit()];
        buffer.get(bb);

        return bb;
    }

    public static Pojo fromByteArray(byte[] bytes) {
        require(bytes != null && bytes.length > 20, "byteArray should be at least 20 bytes long!");

        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        Pojo pojo = new Pojo();
        pojo.setId(buffer.getInt());
        pojo.setTimestamp(buffer.getLong());
        pojo.setTenantId(Functions.getString(buffer));
        pojo.setPayload(Functions.getString(buffer));

        return pojo;
    }


}