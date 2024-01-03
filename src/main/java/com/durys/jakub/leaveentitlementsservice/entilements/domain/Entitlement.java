package com.durys.jakub.leaveentitlementsservice.entilements.domain;

import com.durys.jakub.leaveentitlementsservice.common.exception.DomainValidationException;
import lombok.AccessLevel;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter(AccessLevel.PACKAGE)
class Entitlement {

    private final EntitlementId id;
    private Period period;
    private final Amount entitled;
    private final List<Absence> absences;

    Entitlement(EntitlementId id, Period period, Amount amountEntitled) {
        this.id = id;
        this.period = period;
        this.entitled = amountEntitled;
        this.absences = new ArrayList<>();
    }

    Entitlement(UUID id, LocalDate from, LocalDate to, Integer days) {
        this.id = new EntitlementId(id);
        this.period = new Period(from, to);
        this.entitled = new Amount(days);
        this.absences = new ArrayList<>();
    }

    void addAbsence(Absence absence) {

        if (containsAbsenceAt(absence.at())) {
            throw new DomainValidationException("Absence already exists at %s".formatted(absence.at()));
        }

        absences.add(absence);
    }

    void withdrawAbsence(AbsenceId absenceId) {
        absences
            .removeIf(absence -> absence.id().equals(absenceId));
    }

    Integer remainingAmount() {
        return entitled.days() - amountUsed();
    }


    private Integer amountUsed() {
        return absences.size();
    }

    boolean containsAbsenceAt(LocalDate at) {
        return absences.stream().anyMatch(absence -> absence.at().equals(at));
    }

    LocalDate from() {
        return period.from();
    }

    LocalDate to() {
        return period.to();
    }


    boolean applicable(LocalDate at) {
        return !at.isBefore(from()) && !at.isAfter(to());
    }

    Entitlement terminate(LocalDate at) {
        period = new Period(period.from(), at);
        return null;
    }
}
