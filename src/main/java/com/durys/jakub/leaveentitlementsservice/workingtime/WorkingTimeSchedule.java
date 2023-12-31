package com.durys.jakub.leaveentitlementsservice.workingtime;

import com.durys.jakub.leaveentitlementsservice.common.exception.DomainValidationException;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public record WorkingTimeSchedule(LocalDate from, LocalDate to, Set<Day> days) {

    public WorkingTimeSchedule {

        if (Objects.isNull(from)) {
            throw new DomainValidationException("Date from cannot be empty");
        }

        if (Objects.isNull(to)) {
            throw new DomainValidationException("Date to cannot be empty");
        }

        if (from.isAfter(to)) {
            throw new DomainValidationException("Invalid period");
        }

        if (CollectionUtils.isEmpty(days)) {
            throw new DomainValidationException("Invalid days definition");
        }

    }


    public Long numberOfDays() {
        return (long) days.size();
    }

    public Long numberOfWorkingDays() {
        return days.stream()
                .filter(day -> Objects.nonNull(day.hours()))
                .count();
    }

    public BigDecimal hours() {
        return days.stream()
                .filter(day -> Objects.nonNull(day.hours()))
                .map(Day::hours)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal hoursInRange(LocalDate from, LocalDate to) {
        return  loadInRange(from, to)
                .stream()
                .filter(day -> Objects.nonNull(day.hours()))
                .map(Day::hours)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Set<Day> loadInRange(LocalDate from, LocalDate to) {
        return days.stream()
                .filter(day -> !day.at().isBefore(from) && !day.at().isAfter(to))
                .collect(Collectors.toSet());
    }

    public Long numberOfWorkingDaysInRange(LocalDate from, LocalDate to) {
        return loadInRange(from, to)
                .stream()
                .filter(day -> Objects.nonNull(day.hours()))
                .count();
    }

}
