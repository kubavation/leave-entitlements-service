package com.durys.jakub.leaveentitlementsservice.entilements.domain;

import com.durys.jakub.leaveentitlementsservice.common.exception.DomainValidationException;
import lombok.AccessLevel;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter(AccessLevel.PACKAGE)
class Entitlement {

    private final Period period;
    private final Amount amount;
    private final List<Absence> absences;

    Entitlement(Period period, Amount amount) {
        this.period = period;
        this.amount = amount;
        this.absences = new ArrayList<>();
    }

    Entitlement(LocalDate from, LocalDate to, Integer days) {
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

    void withdrawAbsence(LocalDate from, LocalDate to) {

        absences
                .removeIf(absence -> !absence.at().isBefore(from) && !absence.at().isAfter(to));

    }


    boolean containsAbsenceAt(LocalDate at) {
        return absences.stream().anyMatch(absence -> absence.at().equals(at));
    }

}
