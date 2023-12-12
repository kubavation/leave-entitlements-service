package com.durys.jakub.leaveentitlementsservice.es;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public final class Event {
    private final UUID id;
    private final UUID aggregateId;
    private String type;
    private final Instant at;
    private final byte[] data;

    public Event(UUID id, UUID aggregateId, String type, Instant at, byte[] data) {
        this.id = id;
        this.aggregateId = aggregateId;
        this.type = type;
        this.at = at;
        this.data = data;
    }

    public Event(UUID id, UUID aggregateId, String type, byte[] data) {
        this(id, aggregateId, type, Instant.now(), data);
    }


}
