package com.durys.jakub.leaveentitlementsservice.entilements.domain;

import com.durys.jakub.leaveentitlementsservice.absence.domain.AbsenceConfiguration;
import com.durys.jakub.leaveentitlementsservice.common.exception.DomainValidationException;
import com.durys.jakub.leaveentitlementsservice.workingtime.WorkingTimeSchedule;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Details {

    private final Set<Entitlement> entitlements;

    Details(Set<Entitlement> entitlements) {
        this.entitlements = entitlements;
    }

    Details() {
        this.entitlements = new HashSet<>();
    }


    void add(Entitlement entitlement) {
        entitlements.add(entitlement);
    }

    void appendAbsence(Absence absence) {

        Entitlement entitlement = findEntitlement(absence.at())
                .orElseThrow(RuntimeException::new);

        entitlement.addAbsence(absence);
    }



    void withdrawAbsence(AbsenceId absenceId) {
        entitlements
                .forEach(entitlement -> entitlement.withdrawAbsence(absenceId));
    }


    private Optional<Entitlement> findEntitlement(LocalDate date) {
        return entitlements.stream()
                .filter(entitlement -> entitlement.applicable(date))
                .findFirst();
    }

    boolean entitlementsNotRegistered(LocalDate from, LocalDate to) {
        return Stream.iterate(from, date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(from, to) + 1)
                .map(this::findEntitlement)
                .anyMatch(Optional::isEmpty);
    }

    private Set<Entitlement> findEntitlements(LocalDate from, LocalDate to) {
        return Stream.iterate(from, date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(from, to) + 1)
                .flatMap(date -> findEntitlement(date).stream())
                .collect(Collectors.toSet());
    }


    boolean containsEntitlements(LocalDate from, LocalDate to) {
        return entitlements.stream()
                .map(Entitlement::getPeriod)
                .anyMatch(period -> !(to.isBefore(period.from()) || from.isAfter(period.to())));
    }

    private Integer availableDaysTo(LocalDate date) {
        return entitlements.stream()
                .filter(entitlement -> !date.isAfter(entitlement.to()))
                .mapToInt(Entitlement::remainingAmount)
                .sum();
    }

    void validateAmount(WorkingTimeSchedule schedule, AbsenceConfiguration absence) {

        if (!absence.overdueAvailable()) {
            validateAmount(schedule);
        } else {
            validateOverdueAmount(schedule);
        }
    }

    private void validateOverdueAmount(WorkingTimeSchedule schedule) {
        long numberOfDays = schedule.numberOfWorkingDays();

        Integer remainingAmount = availableDaysTo(schedule.to());

        if (numberOfDays > remainingAmount) {
            throw new DomainValidationException("Amount days of absence exceeds entitlement amount");
        }
    }

    private void validateAmount(WorkingTimeSchedule schedule) {
        entitlements.stream()
                .forEach(entitlement -> {

                    long numberOfDays = schedule
                            .numberOfWorkingDaysInRange(entitlement.from(), entitlement.to());

                    if (numberOfDays > entitlement.remainingAmount()) {
                        throw new DomainValidationException("Amount days of absence exceeds entitlement amount");
                    }
                });
    }
}
