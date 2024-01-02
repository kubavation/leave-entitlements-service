package com.durys.jakub.leaveentitlementsservice.employee.application.event;

import java.time.LocalDate;
import java.util.UUID;

public record UserDismissed(UUID tenantId, LocalDate dismissalDate) {
}
