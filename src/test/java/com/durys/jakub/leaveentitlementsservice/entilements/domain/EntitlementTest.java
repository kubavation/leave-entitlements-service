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

    @Test
    void shouldWithdrawAbsence() {
        var absence = new Absence(UUID.randomUUID(), LocalDate.of(2023, 5, 5));
        Entitlement entitlement = init(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));
        entitlement.addAbsence(absence);


        entitlement.withdrawAbsence(absence.id());

        assertFalse(entitlement.containsAbsenceAt(absence.at()));
    }

    @Test
    void shouldReturnRemainingAmount() {
        var absence = new Absence(UUID.randomUUID(), LocalDate.of(2023, 5, 5));
        Entitlement entitlement = init(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31), 31);

        entitlement.addAbsence(absence);

        assertEquals(30, entitlement.remainingAmount());
    }

    Entitlement init(LocalDate from, LocalDate to) {
        return init(from, to, 26);
    }

    Entitlement init(LocalDate from, LocalDate to, Integer days) {
        return new Entitlement(UUID.randomUUID(), from, to, days);
    }


}