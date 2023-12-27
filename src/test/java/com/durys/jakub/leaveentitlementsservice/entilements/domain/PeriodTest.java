package com.durys.jakub.leaveentitlementsservice.entilements.domain;

import com.durys.jakub.leaveentitlementsservice.common.exception.DomainValidationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PeriodTest {

    @Test
    void shouldCreatePeriod() {
        assertDoesNotThrow(() -> new Period(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 2)));
    }


    @Test
    void shouldNotCreatePeriod_whenDateIsEmpty() {
        DomainValidationException exception = assertThrows(DomainValidationException.class,
                () -> new Period(LocalDate.of(2023, 1, 1), null));
        assertEquals("Date cannot be empty", exception.getMessage());
    }

    @Test
    void shouldNotCreatePeriod_whenDatesAreInvalid() {
        DomainValidationException exception = assertThrows(DomainValidationException.class,
                () -> new Period(LocalDate.of(2023, 1, 2), LocalDate.of(2023, 1, 1)));
        assertEquals("Invalid period", exception.getMessage());
    }

}