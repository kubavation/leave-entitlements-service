package com.durys.jakub.leaveentitlementsservice.absence.domain;

public record AbsenceConfiguration(boolean overdueAvailable, Settlement settlement) {

    public enum Settlement {
        Days, Hours
    }

}
