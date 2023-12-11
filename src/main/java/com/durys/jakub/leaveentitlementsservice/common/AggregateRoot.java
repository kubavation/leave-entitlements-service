package com.durys.jakub.leaveentitlementsservice.common;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class AggregateRoot {

    protected final UUID id;
    protected String type;
    protected Long version;
    protected final List<Event> events = new ArrayList<>();

    AggregateRoot(UUID id, Class<?> type) {
        this.id = id;
        this.type = type.getName();
    }
}
