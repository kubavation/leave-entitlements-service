package com.durys.jakub.leaveentitlementsservice.employee.domain;

import com.durys.jakub.leaveentitlementsservice.sharedkernel.TenantId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmploymentFactory {


    public static Employment create(UUID tenantId, BigDecimal post, LocalDate employmentDate) {
        return new Employment(new TenantId(tenantId), new Post(post), new EmploymentDate(employmentDate), null);
    }

}
