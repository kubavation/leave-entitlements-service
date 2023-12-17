package com.durys.jakub.leaveentitlementsservice.workingtime;

import java.time.LocalDate;
import java.util.UUID;

public interface WorkingTimeScheduleRepository {
    WorkingTimeSchedule load(UUID tenantId, LocalDate from, LocalDate to);
}
