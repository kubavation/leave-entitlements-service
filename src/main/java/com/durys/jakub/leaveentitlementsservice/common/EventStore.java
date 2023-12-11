package com.durys.jakub.leaveentitlementsservice.common;

import java.util.List;
import java.util.UUID;

public interface EventStore {
    <T extends AggregateRoot> T load(UUID aggregateId);
    <T extends AggregateRoot> void save(T aggregate);
    void save(List<Event> events);
    List<Event> loadEvents(UUID aggregateId);
}
