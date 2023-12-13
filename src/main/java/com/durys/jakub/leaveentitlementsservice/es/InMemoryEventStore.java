package com.durys.jakub.leaveentitlementsservice.es;

import com.durys.jakub.leaveentitlementsservice.ddd.AggregateRoot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
class InMemoryEventStore implements EventStore {

    private static final Map<Object, List<Event>> DB = new ConcurrentHashMap<>();


    @Override
    public <T extends AggregateRoot> T load(Object aggregateId, Class<T> type) {

        var aggregate = getAggregate(aggregateId, type);

        List<Event> events = loadEvents(aggregateId, type);

        events
            .forEach(aggregate::raise);

        return aggregate;
    }

    @Override
    public <T extends AggregateRoot> void save(T aggregate) {

        saveEvents(aggregate.getId(), aggregate.getEvents());
        //todo
    }

    @Override
    public void saveEvents(Object aggregateId, List<Event> events) {

        List<Event> loadedEvents = DB.get(aggregateId);
        loadedEvents.addAll(events);

        DB.put(aggregateId, loadedEvents);
    }

    @Override
    public <T extends AggregateRoot> List<Event> loadEvents(Object aggregateId, Class<T> type) {
        return DB.get(aggregateId);
    }


    <T extends AggregateRoot> T getAggregate(Object aggregateId, Class<T> type) {
        try {
            return type.getConstructor(aggregateId.getClass()).newInstance(aggregateId);
        } catch (Exception e) {
            log.error("getAggregate error", e);
            throw new RuntimeException("Cannot construct aggregate ID: %s TYPE: %s".formatted(aggregateId.toString(), type.getName()));
        }
    }
}
