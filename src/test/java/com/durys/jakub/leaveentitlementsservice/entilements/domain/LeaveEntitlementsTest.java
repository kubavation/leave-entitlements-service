package com.durys.jakub.leaveentitlementsservice.entilements.domain;

import com.durys.jakub.leaveentitlementsservice.common.exception.DomainValidationException;
import com.durys.jakub.leaveentitlementsservice.entilements.domain.events.LeaveEntitlementsEvent;
import jakarta.xml.bind.ValidationException;
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

        LeaveEntitlements entitlements = init();

        entitlements.grantEntitlements(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31), 26);

        assertTrue(entitlements.getEvents().stream().anyMatch(event -> event instanceof LeaveEntitlementsEvent.LeaveEntitlementsGranted));
    }



    @Test
    void shouldNotGrantLeaveEntitlements_whenAlreadyExistsInSpecifiedPeriod() {

        LeaveEntitlements entitlements = init();
        entitlements.grantEntitlements(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31), 26);

        DomainValidationException exception = assertThrows(DomainValidationException.class, () ->
                entitlements.grantEntitlements(LocalDate.of(2023, 2, 1), LocalDate.of(2024, 2, 28), 26));

        assertEquals("Entitlements already exists in period", exception.getMessage());
    }

    private LeaveEntitlements init() {
        return LeaveEntitlementsFactory.create("W", UUID.randomUUID());
    }


}