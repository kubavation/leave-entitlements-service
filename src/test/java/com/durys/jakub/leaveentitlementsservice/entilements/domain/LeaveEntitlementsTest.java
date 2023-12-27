package com.durys.jakub.leaveentitlementsservice.entilements.domain;

import com.durys.jakub.leaveentitlementsservice.absence.domain.AbsenceConfiguration;
import com.durys.jakub.leaveentitlementsservice.common.exception.DomainValidationException;
import com.durys.jakub.leaveentitlementsservice.entilements.domain.events.LeaveEntitlementsEvent;
import com.durys.jakub.leaveentitlementsservice.workingtime.Day;
import com.durys.jakub.leaveentitlementsservice.workingtime.WorkingTimeSchedule;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Test
    void shouldAppendAbsence() {

        LeaveEntitlements entitlements = init();
        entitlements.grantEntitlements(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31), 26);

        var schedule = new WorkingTimeSchedule(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 3),
                generate(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 3)));

        var absence = new AbsenceConfiguration(false, AbsenceConfiguration.Settlement.Days);

        entitlements.appendAbsence(schedule, absence);

        assertTrue(entitlements.getEvents().stream().anyMatch(event -> event instanceof LeaveEntitlementsEvent.AbsenceAppended));
    }



    private LeaveEntitlements init() {
        return LeaveEntitlementsFactory.create("W", UUID.randomUUID());
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