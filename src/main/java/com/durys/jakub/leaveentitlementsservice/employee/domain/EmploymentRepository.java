package com.durys.jakub.leaveentitlementsservice.employee.domain;

public interface EmploymentRepository {
    Employment load(TenantId tenantId);
}
