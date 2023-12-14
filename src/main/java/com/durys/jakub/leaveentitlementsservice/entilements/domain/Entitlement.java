package com.durys.jakub.leaveentitlementsservice.entilements.domain;

import java.time.LocalDate;

class Entitlement {

    private final Period period;
    private final Amount amount;

    Entitlement(Period period, Amount amount) {
        this.period = period;
        this.amount = amount;
    }

    Entitlement(LocalDate from, LocalDate to, Integer days) {
        this.period = new Period(from, to);
        this.amount = new Amount(days);
    }
}
