package com.durys.jakub.leaveentitlementsservice.entilements;

import java.util.UUID;

public class LeaveEntitlement {

    public record Id(AbsenceType absenceType, TenantId tenantId) {

        public Id(String absenceType, UUID tenantId) {
            this(new AbsenceType(absenceType), new TenantId(tenantId));
        }

    }



}
