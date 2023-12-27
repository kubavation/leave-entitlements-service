package com.durys.jakub.leaveentitlementsservice.workingtime;

import com.durys.jakub.leaveentitlementsservice.common.exception.DomainValidationException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DayTest {

    @Test
    void shouldCreateDay() {
        assertDoesNotThrow(() -> new Day(LocalDate.of(2023, 1, 1), BigDecimal.valueOf(8)));
    }

    @Test
    void shouldNotCreateDay_whenDateIsEmpty() {
        DomainValidationException exception
                = assertThrows(DomainValidationException.class, () -> new Day(null, BigDecimal.valueOf(8)));
        assertEquals("Date cannot be empty", exception.getMessage());
    }
    

    @Test
    void shouldNotCreateDay_whenHoursAreLessThanZero() {
        DomainValidationException exception
                = assertThrows(DomainValidationException.class, () -> new Day(LocalDate.of(2023, 1, 1), new BigDecimal(-1)));
        assertEquals("Invalid hours value", exception.getMessage());
    }


}