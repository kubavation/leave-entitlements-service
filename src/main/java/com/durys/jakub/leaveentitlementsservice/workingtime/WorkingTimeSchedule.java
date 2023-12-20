package com.durys.jakub.leaveentitlementsservice.workingtime;

import java.util.Objects;
import java.util.Set;

public record WorkingTimeSchedule(Set<Day> days) {


    public Long numberOfDays() {
        return (long) days.size();
    }

    public Long numberOfWorkingDays() {
        return days.stream()
                .filter(day -> Objects.nonNull(day.hours()))
                .count();
    }

}
