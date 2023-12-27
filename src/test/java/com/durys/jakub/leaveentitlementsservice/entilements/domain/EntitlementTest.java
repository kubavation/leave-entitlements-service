package com.durys.jakub.leaveentitlementsservice.entilements.domain;

import com.durys.jakub.leaveentitlementsservice.common.exception.DomainValidationException;
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

    @Test
    void shouldAddAbsence() {
        var absence = new Absence(UUID.randomUUID(), LocalDate.of(2023, 5, 5));
        Entitlement entitlement = init(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));

        entitlement.addAbsence(absence);

        assertFalse(entitlement.getAbsences().isEmpty());
    }

    @Test
    void shouldNotAddAbsence_whenAbsenceAlreadyExists() {
        var absence = new Absence(UUID.randomUUID(), LocalDate.of(2023, 5, 5));
        Entitlement entitlement = init(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));
        entitlement.addAbsence(new Absence(UUID.randomUUID(), absence.at()));

        DomainValidationException exception = assertThrows(DomainValidationException.class, () -> entitlement.addAbsence(absence));

        assertEquals("Absence already exists at %s".formatted(absence.at()), exception.getMessage());
    }

    Entitlement init(LocalDate from, LocalDate to) {
        return new Entitlement(UUID.randomUUID(), from, to, 26);
    }

}