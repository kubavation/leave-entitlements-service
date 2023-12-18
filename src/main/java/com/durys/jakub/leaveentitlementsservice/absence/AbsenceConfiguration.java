package com.durys.jakub.leaveentitlementsservice.absence;

public class AbsenceConfiguration {

    public enum Settlement {
        Days, Hours
    }

    private final boolean overdueLeave;
    private final Settlement settlement;

    //todo

    AbsenceConfiguration(boolean overdueLeave, Settlement settlement) {
        this.overdueLeave = overdueLeave;
        this.settlement = settlement;
    }
}
