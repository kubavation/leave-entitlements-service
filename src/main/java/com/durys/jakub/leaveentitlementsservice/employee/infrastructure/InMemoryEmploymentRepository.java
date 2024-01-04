package com.durys.jakub.leaveentitlementsservice.employee.infrastructure;

import com.durys.jakub.leaveentitlementsservice.employee.domain.Employment;
import com.durys.jakub.leaveentitlementsservice.employee.domain.EmploymentRepository;
import com.durys.jakub.leaveentitlementsservice.sharedkernel.TenantId;

import java.util.HashMap;
import java.util.Map;

public class InMemoryEmploymentRepository implements EmploymentRepository {

    private static final Map<TenantId, Employment> DB = new HashMap<>();

    @Override
    public Employment load(TenantId tenantId) {
        return DB.get(tenantId);
    }

    @Override
    public void save(Employment employment) {
        DB.put(employment.tenantId(), employment);
    }
}
