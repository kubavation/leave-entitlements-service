package com.durys.jakub.leaveentitlementsservice.common;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
class InMemoryEventStore implements EventStore {

    private static final Map<UUID, AggregateRoot> DB = new ConcurrentHashMap<>();

    @Override
    public <T extends AggregateRoot> T load(UUID aggregateId) {
        return null;
    }

    @Override
    public <T extends AggregateRoot> void save(T aggregate) {

    }

    @Override
    public void save(List<Event> events) {

    }

    @Override
    public <T extends AggregateRoot> List<Event> loadEvents(UUID aggregateId, Class<T> type) {

        AggregateRoot aggregateRoot = DB.get(aggregateId);

        if (Objects.isNull(aggregateRoot)) {
            return Collections.emptyList();
        }

        return aggregateRoot.events;
    }
}
