package com.durys.jakub.leaveentitlementsservice.entilements.domain;

class Entitlement {

    private final Period period;
    private final Amount amount;

    Entitlement(Period period, Amount amount) {
        this.period = period;
        this.amount = amount;
    }
}
