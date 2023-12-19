package com.durys.jakub.leaveentitlementsservice.entilements.infrastructure;

import com.durys.jakub.leaveentitlementsservice.common.serialization.Serializer;
import com.durys.jakub.leaveentitlementsservice.entilements.domain.LeaveEntitlements;
import com.durys.jakub.leaveentitlementsservice.entilements.domain.LeaveEntitlementsRepository;
import com.durys.jakub.leaveentitlementsservice.entilements.domain.events.LeaveEntitlementsEvent;
import com.durys.jakub.leaveentitlementsservice.es.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryLeaveEntitlementsRepository implements LeaveEntitlementsRepository {

    static final Map<LeaveEntitlements.Id, List<Event>> DB = new ConcurrentHashMap<>();

    @Override
    public LeaveEntitlements load(LeaveEntitlements.Id id) {

        List<Event> events = DB.get(id);

        List<LeaveEntitlementsEvent> domainEvents = events.stream()
                .map(event -> {
                    try {
                        return (LeaveEntitlementsEvent) Serializer.deserialize(event.getData(), Class.forName(event.getClazz()));
                    } catch (ClassNotFoundException e) {
                        return null;
                    }
                })
                .toList();

        return LeaveEntitlements.recreate(domainEvents);
    }

    @Override
    public void save(LeaveEntitlements entitlements) {

        List<Event> pendingEvents = entitlements.getEvents()
                .stream()
                .map(event -> new Event(event.aggregateId(), event.getClass(), Serializer.serialize(event)))
                .toList();

        List<Event> events = getEvents(entitlements.id());
        events.addAll(pendingEvents);

        DB.put(entitlements.id(), events);
    }

    private List<Event> getEvents(LeaveEntitlements.Id id) {

        List<Event> events = DB.get(id);

        if (Objects.isNull(events)) {
            return new ArrayList<>();
        }

        return events;
    }
}
