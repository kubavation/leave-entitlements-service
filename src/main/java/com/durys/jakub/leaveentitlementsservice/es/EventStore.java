package com.durys.jakub.leaveentitlementsservice.es;

import com.durys.jakub.leaveentitlementsservice.cqrs.DomainEvent;
import com.durys.jakub.leaveentitlementsservice.ddd.AggregateRoot;

import java.util.List;

public interface EventStore<T extends AggregateRoot<R>, R extends DomainEvent> {
    T load(Object aggregateId);
    void save(T aggregate);
    void saveEvents(Object aggregateId, List<Event> events);
    List<Event> loadEvents(Object aggregateId);
}
