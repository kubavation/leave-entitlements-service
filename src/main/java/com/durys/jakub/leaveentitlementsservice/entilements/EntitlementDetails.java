package com.durys.jakub.leaveentitlementsservice.entilements;

class EntitlementDetails {

    private final Period period;
    private final Amount amount;

    EntitlementDetails(Period period, Amount amount) {
        this.period = period;
        this.amount = amount;
    }
}
