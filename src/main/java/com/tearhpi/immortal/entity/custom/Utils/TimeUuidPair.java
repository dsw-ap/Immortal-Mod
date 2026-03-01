package com.tearhpi.immortal.entity.custom.Utils;

import java.util.UUID;

public class TimeUuidPair {
    public long time;
    public UUID uuid;

    public TimeUuidPair(long time, UUID uuid) {
        this.time = time;
        this.uuid = uuid;
    }

    public long getTime() {
        return time;
    }

    public UUID getUuid() {
        return uuid;
    }
}
