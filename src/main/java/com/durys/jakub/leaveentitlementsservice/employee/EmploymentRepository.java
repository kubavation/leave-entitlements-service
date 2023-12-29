package com.durys.jakub.leaveentitlementsservice.employee;

public interface EmploymentRepository {
    Employment load(TenantId tenantId);
}
