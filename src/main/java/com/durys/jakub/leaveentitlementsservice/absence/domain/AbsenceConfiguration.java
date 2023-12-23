package com.durys.jakub.leaveentitlementsservice.absence.domain;

public record AbsenceConfiguration(boolean overdueAvailable, AbsenceConfiguration.Settlement settlement) {

    public enum Settlement {
        Days, Hours
    }

}
