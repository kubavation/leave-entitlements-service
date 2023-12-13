package com.durys.jakub.leaveentitlementsservice.es;

import com.durys.jakub.leaveentitlementsservice.entilements.domain.LeaveEntitlements;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryEventStoreTest {

    private final InMemoryEventStore eventStore = new InMemoryEventStore();

    @Test
    void shouldGetAggregate() {

        var id = new LeaveEntitlements.Id("W", UUID.randomUUID());

        LeaveEntitlements aggregate = eventStore.getAggregate(id, LeaveEntitlements.class);

        assertNotNull(aggregate);
    }

}