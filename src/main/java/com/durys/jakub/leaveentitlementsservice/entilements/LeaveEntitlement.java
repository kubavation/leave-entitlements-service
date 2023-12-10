package com.durys.jakub.leaveentitlementsservice.entilements;

public class LeaveEntitlement {

    public record Id(AbsenceType absenceType, TenantId tenantId) {
        
    }

}
