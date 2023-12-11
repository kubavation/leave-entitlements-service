package com.durys.jakub.leaveentitlementsservice.common;

import java.time.Instant;
import java.util.UUID;

public record Event(UUID id, UUID aggregateId, String type, Instant at, byte[] data) {

    public Event(UUID id, UUID aggregateId, String type, byte[] data) {
        this(id, aggregateId, type, Instant.now(), data);
    }
}
