package com.durys.jakub.leaveentitlementsservice.employee.infrastructure;

import com.durys.jakub.leaveentitlementsservice.employee.domain.Employment;
import com.durys.jakub.leaveentitlementsservice.employee.domain.EmploymentRepository;

import java.util.HashMap;
import java.util.Map;

public class InMemoryEmploymentRepository implements EmploymentRepository {

    private static final Map<TenantId, Employment> DB = new HashMap<>();

    @Override
    public Employment load(TenantId tenantId) {
        return DB.get(tenantId);
    }
}
