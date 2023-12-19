package com.durys.jakub.leaveentitlementsservice.absence.domain;

public class AbsenceConfiguration {

    public enum Settlement {
        Days, Hours
    }

    private final boolean overdueAvailable;
    private final Settlement settlement;

    AbsenceConfiguration(boolean overdueAvailable, Settlement settlement) {
        this.overdueAvailable = overdueAvailable;
        this.settlement = settlement;
    }

    public boolean overdueAvailable() {
        return overdueAvailable;
    }

    public Settlement settlement() {
        return settlement;
    }
}
