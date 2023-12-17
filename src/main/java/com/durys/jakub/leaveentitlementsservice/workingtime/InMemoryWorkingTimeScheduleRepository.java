package com.durys.jakub.leaveentitlementsservice.workingtime;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

class InMemoryWorkingTimeScheduleRepository implements WorkingTimeScheduleRepository {


    @Override
    public WorkingTimeSchedule load(UUID tenantId, LocalDate from, LocalDate to) {

        long days = ChronoUnit.DAYS.between(from, to) + 1;

        return new WorkingTimeSchedule(days, new BigDecimal(days * 8));
    }
}
