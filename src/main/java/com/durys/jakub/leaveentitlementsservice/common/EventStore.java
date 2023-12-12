package com.durys.jakub.leaveentitlementsservice.common;

import java.util.List;

public interface EventStore {
    <T extends AggregateRoot> T load(Object aggregateId, Class<T> type);
    <T extends AggregateRoot> void save(T aggregate);
    void saveEvents(Object aggregateId, List<Event> events);
    <T extends AggregateRoot> List<Event> loadEvents(Object aggregateId, Class<T> type);
}
