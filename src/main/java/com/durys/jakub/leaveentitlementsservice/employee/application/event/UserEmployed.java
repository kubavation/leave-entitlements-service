package com.durys.jakub.leaveentitlementsservice.employee.application.event;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record UserEmployed(UUID tenantId, BigDecimal post, LocalDate employmentDate) {
}
