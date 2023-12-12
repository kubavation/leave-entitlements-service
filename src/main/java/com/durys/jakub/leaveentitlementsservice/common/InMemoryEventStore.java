package com.durys.jakub.leaveentitlementsservice.common;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
class InMemoryEventStore implements EventStore {

    private static final Map<UUID, List<Event>> DB = new ConcurrentHashMap<>();


    @Override
    public <T extends AggregateRoot> T load(UUID aggregateId, Class<T> type) {

        var aggregate = getAggregate(aggregateId, type);

        List<Event> events = loadEvents(aggregateId, type);

        events
            .forEach(aggregate::raise);

        return aggregate;
    }

    @Override
    public <T extends AggregateRoot> void save(T aggregate) {

    }

    @Override
    public void save(List<Event> events) {

    }

    @Override
    public <T extends AggregateRoot> List<Event> loadEvents(UUID aggregateId, Class<T> type) {
        return DB.get(aggregateId);
    }


    private <T extends AggregateRoot> T getAggregate(UUID aggregateId, Class<T> type) {
        try {
            return type.getConstructor(UUID.class, String.class).newInstance(aggregateId, type.getName());
        } catch (Exception e) {
            throw new RuntimeException("Cannot construct aggregate ID: %s TYPE: %s".formatted(aggregateId.toString(), type.getName()));
        }
    }
}
