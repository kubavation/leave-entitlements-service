package com.durys.jakub.leaveentitlementsservice.entilements.infrastructure;

import com.durys.jakub.leaveentitlementsservice.common.serialization.Serializer;
import com.durys.jakub.leaveentitlementsservice.entilements.domain.LeaveEntitlements;
import com.durys.jakub.leaveentitlementsservice.entilements.domain.LeaveEntitlementsRepository;
import com.durys.jakub.leaveentitlementsservice.entilements.domain.events.LeaveEntitlementsEvent;
import com.durys.jakub.leaveentitlementsservice.es.Event;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryLeaveEntitlementsRepository implements LeaveEntitlementsRepository {

    private static final Map<LeaveEntitlements.Id, List<Event>> DB = new ConcurrentHashMap<>();

    @Override
    public LeaveEntitlements load(LeaveEntitlements.Id id) {
        List<Event> events = DB.get(id);

        List<LeaveEntitlementsEvent> domainEvents = events.stream()
                .map(event -> Serializer.deserialize(event.getData(), LeaveEntitlementsEvent.class))
                .toList();

        return LeaveEntitlements.recreate(domainEvents);
    }

    @Override
    public void save(LeaveEntitlements entitlements) {

        List<Event> events = entitlements.getEvents()
                .stream()
                .map(event -> new Event(event.aggregateId(), event.getClass().getSimpleName(), Serializer.serialize(event)))
                .toList();

        DB.put(entitlements.id(), events);
    }
}
