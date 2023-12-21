package com.durys.jakub.leaveentitlementsservice.workingtime;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class InMemoryWorkingTimeScheduleRepository implements WorkingTimeScheduleRepository {


    @Override
    public WorkingTimeSchedule load(UUID tenantId, LocalDate from, LocalDate to) {

        var days = Stream.iterate(from, date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(from, to) + 1)
                .map(date -> new Day(date, BigDecimal.valueOf(8)))
                .collect(Collectors.toSet());

        return new WorkingTimeSchedule(days);
    }
}
