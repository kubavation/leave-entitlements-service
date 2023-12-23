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
    private final Period period;
    private final Amount amount;
    private final List<Absence> absences;

    Entitlement(EntitlementId id, Period period, Amount amount) {
        this.id = id;
        this.period = period;
        this.amount = amount;
        this.absences = new ArrayList<>();
    }

    Entitlement(UUID id, LocalDate from, LocalDate to, Integer days) {
        this.id = new EntitlementId(id);
        this.period = new Period(from, to);
        this.amount = new Amount(days);
        this.absences = new ArrayList<>();
    }

    void addAbsence(Absence absence) {

        if (containsAbsenceAt(absence.at())) {
            throw new DomainValidationException("Absence already exists at %s".formatted(absence.at()));
        }

        absences.add(absence);
    }

    void withdrawAbsence(UUID absenceId) {

        absences
                .removeIf(absence -> absence.id().equals(absenceId));

    }

    Integer remainingAmount() {
        return amount.days() - amountUsed();
    }


    private Integer amountUsed() {
        return absences.size();
    }

    private boolean containsAbsenceAt(LocalDate at) {
        return absences.stream().anyMatch(absence -> absence.at().equals(at));
    }

    LocalDate from() {
        return period.from();
    }

    LocalDate to() {
        return period.to();
    }
}
