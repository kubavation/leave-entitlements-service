package com.durys.jakub.leaveentitlementsservice.workingtime;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public record WorkingTimeSchedule(Set<Day> days) {


    public Long numberOfDays() {
        return (long) days.size();
    }

    public Long numberOfWorkingDays() {
        return days.stream()
                .filter(day -> Objects.nonNull(day.hours()))
                .count();
    }

    public Set<Day> loadInRange(LocalDate from, LocalDate to) {
        //todo validation
        return days.stream()
                .filter(day -> !day.at().isBefore(from) && !day.at().isAfter(to))
                .collect(Collectors.toSet());
    }

}
