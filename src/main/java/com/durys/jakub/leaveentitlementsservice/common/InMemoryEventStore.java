package com.durys.jakub.leaveentitlementsservice.common;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
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
    public List<Event> loadEvents(UUID aggregateId) {
        return null;
    }
}
