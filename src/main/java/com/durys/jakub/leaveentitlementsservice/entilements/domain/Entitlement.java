package com.durys.jakub.leaveentitlementsservice.entilements.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

        if (containsAbsenceAt(absence.getAt())) {
            throw new RuntimeException("Absence already exists at %s".formatted(absence.getAt()));
        }

        absences.add(absence);
    }


    boolean containsAbsenceAt(LocalDate at) {
        return absences.stream().anyMatch(absence -> absence.getAt().equals(at));
    }
}
