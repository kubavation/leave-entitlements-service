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
                .filter(entitlement -> !date.isBefore(entitlement.getPeriod().from()) && !date.isAfter(entitlement.getPeriod().to()))
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
                .anyMatch(period -> !from.isBefore(period.from()) && !to.isAfter(period.to()));
    }

    private Integer availableDaysTo(LocalDate date) {
        return entitlements.stream()
                .filter(entitlement -> !date.isAfter(entitlement.to()))
                .mapToInt(Entitlement::remainingAmount)
                .sum();
    }

    void validateAmount(LocalDate from, LocalDate to, WorkingTimeSchedule workingTimeSchedule, AbsenceConfiguration absence) {

        if (!absence.overdueAvailable()) {

            entitlements.stream()
                    .forEach(entitlement -> {

                        long numberOfDays = workingTimeSchedule
                                .numberOfWorkingDaysInRange(entitlement.from(), entitlement.to());

                        if (numberOfDays > entitlement.remainingAmount()) {
                            throw new DomainValidationException("Amount days of absence exceeds entitlement amount");
                        }
                    });
        } else {


            long numberOfDays = workingTimeSchedule.numberOfWorkingDays();

            Integer remainingAmount = availableDaysTo(to);

            if (numberOfDays > remainingAmount) {
                throw new DomainValidationException("Amount days of absence exceeds entitlement amount");
            }

        }
    }
}
