package com.durys.jakub.leaveentitlementsservice.es;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public final class Event {
    private final UUID id;
    private final Object aggregateId;
    private String type;
    private String aggregateType;
    private final Instant at;
    private final byte[] data;
    private String clazz;

    public Event(UUID id, Object aggregateId, String type, Instant at, byte[] data, String clazz) {
        this.id = id;
        this.aggregateId = aggregateId;
        this.type = type;
        this.at = at;
        this.data = data;
        this.clazz = clazz;
    }


    public Event(Object aggregateId, Class<?> type, byte[] data) {
        this(UUID.randomUUID(), aggregateId, type.getSimpleName(), Instant.now(), data, type.getName());
    }


}
