package com.durys.jakub.leaveentitlementsservice.workingtime;

import com.durys.jakub.leaveentitlementsservice.common.exception.DomainValidationException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class WorkingTimeScheduleTest {


    @Test
    void shouldCreateWorkingTimeSchedule() {
        assertDoesNotThrow(() -> new WorkingTimeSchedule(LocalDate.of(2023, 1,1), LocalDate.of(2023, 1, 1),
                generate(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 1))));
    }

    @Test
    void shouldNotCreateWorkingTimeSchedule_whenDatesAreEmpty() {
        DomainValidationException exception = assertThrows(DomainValidationException.class,
                () -> new WorkingTimeSchedule(null, LocalDate.of(2023, 1, 1),
                        generate(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 1))));

        assertEquals("Date from cannot be empty", exception.getMessage());
    }

    @Test
    void shouldNotCreateWorkingTimeSchedule_whenPeriodIsInvalid() {
        DomainValidationException exception = assertThrows(DomainValidationException.class,
                () -> new WorkingTimeSchedule(LocalDate.of(2023, 1,2), LocalDate.of(2023, 1, 1),
                        generate(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 1))));

        assertEquals("Invalid period", exception.getMessage());
    }

    @Test
    void shouldNotCreateWorkingTimeSchedule_whenDaysAreNotDefined() {
        DomainValidationException exception = assertThrows(DomainValidationException.class,
                () -> new WorkingTimeSchedule(LocalDate.of(2023, 1,1), LocalDate.of(2023, 1, 1), null));

        assertEquals("Invalid days definition", exception.getMessage());
    }


    @Test
    void shouldReturnDaysEqualToDaysSize() {

        var from = LocalDate.of(2023, 1, 1);
        var to = LocalDate.of(2023, 1, 3);
        var days = generate(from, to);

        var schedule = new WorkingTimeSchedule(from, to, days);

        assertEquals(days.size(), schedule.numberOfDays());
    }

    @Test
    void shouldReturnCorrectNumberOfWorkingDays() {

        var from = LocalDate.of(2023, 1, 1);
        var to = LocalDate.of(2023, 1, 3);
        var days = generate(from, to, Set.of(LocalDate.of(2023, 1, 2)));

        var schedule = new WorkingTimeSchedule(from, to, days);

        assertEquals(2, schedule.numberOfWorkingDays());
    }



    private static Set<Day> generate(LocalDate from, LocalDate to) {
        return generate(from, to, Set.of());
    }

    private static Set<Day> generate(LocalDate from, LocalDate to, Set<LocalDate> daysOff) {

        return Stream.iterate(from, date -> !date.isAfter(to), date -> date.plusDays(1))
                .map(date -> {
                    if (daysOff.contains(date)) {
                        return new Day(date, null);
                    }
                    return new Day(date, BigDecimal.valueOf(8));
                })
                .collect(Collectors.toSet());
    }


}