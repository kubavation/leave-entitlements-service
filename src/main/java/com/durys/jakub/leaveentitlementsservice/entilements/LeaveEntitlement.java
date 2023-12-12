package com.durys.jakub.leaveentitlementsservice.entilements;

import com.durys.jakub.leaveentitlementsservice.common.AggregateRoot;
import com.durys.jakub.leaveentitlementsservice.common.Event;

import java.util.UUID;

public class LeaveEntitlement extends AggregateRoot {

    private static final String TYPE = "LeaveEntitlement";



    public record Id(AbsenceType absenceType, TenantId tenantId) {

        public Id(String absenceType, UUID tenantId) {
            this(new AbsenceType(absenceType), new TenantId(tenantId));
        }

    }

    private final Id id;

    LeaveEntitlement(Id id) {
        super(id, TYPE);
        this.id = id;
    }

    @Override
    public void handle(Event event) {

    }

}
