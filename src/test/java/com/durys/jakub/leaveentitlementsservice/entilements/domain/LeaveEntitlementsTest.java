package com.durys.jakub.leaveentitlementsservice.entilements.domain;

import com.durys.jakub.leaveentitlementsservice.entilements.domain.events.LeaveEntitlementsEvent;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LeaveEntitlementsTest {

    @Test
    void shouldInitializeLeaveEntitlements() {

        UUID tenantId = UUID.randomUUID();
        String absence = "W";

        LeaveEntitlements entitlements = LeaveEntitlementsFactory.create(absence, tenantId);

        assertTrue(entitlements.getEvents().stream().anyMatch(event -> event instanceof LeaveEntitlementsEvent.LeaveEntitlementsInitialized));
        assertEquals(new LeaveEntitlements.Id(absence, tenantId), entitlements.id());
    }

    @Test
    void shouldGrantLeaveEntitlements() {

        LeaveEntitlements entitlements = LeaveEntitlementsFactory.create("W", UUID.randomUUID());

        entitlements.grantEntitlements(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31), 26);

        assertTrue(entitlements.getEvents().stream().anyMatch(event -> event instanceof LeaveEntitlementsEvent.LeaveEntitlementsGranted));
    }


}