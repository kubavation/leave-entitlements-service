package com.durys.jakub.leaveentitlementsservice.entilements.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EntitlementTest {

    @Test
    void shouldCreateEntitlement() {
        assertDoesNotThrow(() -> new Entitlement(UUID.randomUUID(),
                LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31), 26));
    }

}