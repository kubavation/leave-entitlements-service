package com.durys.jakub.leaveentitlementsservice.es;

import com.durys.jakub.leaveentitlementsservice.entilements.domain.LeaveEntitlements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryEventStoreTest {

    private final InMemoryEventStore eventStore = new InMemoryEventStore();

    @BeforeEach
    void init() {
        eventStore.db().clear();
    }

    @Test
    void shouldGetAggregate() {

        var id = new LeaveEntitlements.Id("W", UUID.randomUUID());

        LeaveEntitlements aggregate = eventStore.getAggregate(id, LeaveEntitlements.class);

        assertNotNull(aggregate);
    }


    @Test
    void shouldSaveAggregate() {

        LeaveEntitlements entitlements = LeaveEntitlements.Factory.create("W", UUID.randomUUID());

        eventStore.save(entitlements);

        assertFalse(eventStore.db().isEmpty());
    }

    @Test
    void shouldLoadAggregate() {

        var id = addAggregate();

        LeaveEntitlements aggregate = eventStore.load(id, LeaveEntitlements.class);

        assertNotNull(aggregate);
    }


    private Object addAggregate() {

        LeaveEntitlements aggregate = LeaveEntitlements.Factory.create("W", UUID.randomUUID());

        eventStore.db().put(aggregate.id(), aggregate.getEvents());

        return aggregate.id();
    }

}