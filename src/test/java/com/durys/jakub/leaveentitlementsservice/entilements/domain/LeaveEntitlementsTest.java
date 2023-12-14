package com.durys.jakub.leaveentitlementsservice.entilements.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LeaveEntitlementsTest {

    @Test
    void shouldInitializeLeaveEntitlements() {

        UUID tenantId = UUID.randomUUID();
        String absence = "W";

        LeaveEntitlements entitlements = LeaveEntitlements.Factory.create(absence, tenantId);

        assertTrue(entitlements.getEvents().stream().anyMatch(event -> event.getType().equals("LeaveEntitlementsInitialized")));
        assertEquals(new LeaveEntitlements.Id(absence, tenantId), entitlements.id());
    }

    @Test
    void shouldGrantLeaveEntitlements() {

        LeaveEntitlements entitlements = LeaveEntitlements.Factory.create("W", UUID.randomUUID());

        entitlements.grantEntitlements(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31), 26);

        assertTrue(entitlements.getEvents().stream().anyMatch(event -> event.getType().equals("LeaveEntitlementsGranted")));
    }


}