package com.durys.jakub.leaveentitlementsservice.common;

import java.util.List;
import java.util.UUID;

public interface EventStore {
    <T extends AggregateRoot> T load(UUID aggregateId, Class<T> type);
    <T extends AggregateRoot> void save(T aggregate);
    void save(List<Event> events);
    <T extends AggregateRoot> List<Event> loadEvents(UUID aggregateId, Class<T> type);
}
