package com.durys.jakub.leaveentitlementsservice.employee.domain.event;

import com.durys.jakub.leaveentitlementsservice.employee.domain.TenantId;

import java.time.LocalDate;

public record EmploymentTerminated(TenantId tenantId, LocalDate at) {
}
