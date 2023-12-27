package com.durys.jakub.leaveentitlementsservice.workingtime;

import com.durys.jakub.leaveentitlementsservice.common.exception.DomainValidationException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class WorkingTimeScheduleTest {


    @Test
    void shouldCreateWorkingTimeSchedule() {
        assertDoesNotThrow(() -> new WorkingTimeSchedule(LocalDate.of(2023, 1,1), LocalDate.of(2023, 1, 1),
                Set.of(
                    new Day(LocalDate.of(2023, 1,1), BigDecimal.valueOf(8))
                )));
    }

    @Test
    void shouldCreateWorkingTimeSchedule_whenDatesAreEmpty() {
        DomainValidationException exception = assertThrows(DomainValidationException.class,
                () -> new WorkingTimeSchedule(null, LocalDate.of(2023, 1, 1),
                    Set.of(
                            new Day(LocalDate.of(2023, 1, 1), BigDecimal.valueOf(8))
                    )));

        assertEquals("Date from cannot be empty", exception.getMessage());
    }

    @Test
    void shouldCreateWorkingTimeSchedule_whenPeriodIsInvalid() {
        DomainValidationException exception = assertThrows(DomainValidationException.class,
                () -> new WorkingTimeSchedule(LocalDate.of(2023, 1,2), LocalDate.of(2023, 1, 1),
                    Set.of(
                            new Day(LocalDate.of(2023, 1,1), BigDecimal.valueOf(8))
                    )));

        assertEquals("Invalid period", exception.getMessage());
    }


//    private static Set<Day> generate(LocalDate from, LocalDate to) {
//
//    }


}