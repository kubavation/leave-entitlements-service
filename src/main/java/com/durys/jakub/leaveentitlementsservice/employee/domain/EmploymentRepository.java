package com.durys.jakub.leaveentitlementsservice.employee.domain;

import com.durys.jakub.leaveentitlementsservice.sharedkernel.TenantId;

public interface EmploymentRepository {
    Employment load(TenantId tenantId);
    void save(Employment employment);
}
