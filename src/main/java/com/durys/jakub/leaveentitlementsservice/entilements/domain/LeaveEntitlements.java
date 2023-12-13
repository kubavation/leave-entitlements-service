package com.durys.jakub.leaveentitlementsservice.entilements.domain;

import com.durys.jakub.leaveentitlementsservice.ddd.AggregateRoot;
import com.durys.jakub.leaveentitlementsservice.es.Event;

import java.util.Set;
import java.util.UUID;

public class LeaveEntitlements extends AggregateRoot {

    private static final String TYPE = "LeaveEntitlement";


    public record Id(AbsenceType absenceType, TenantId tenantId) {

        public Id(String absenceType, UUID tenantId) {
            this(new AbsenceType(absenceType), new TenantId(tenantId));
        }

    }

    public enum State {
        Active, Archived
    }


    private final Id identifier;
    private State state;
    private Set<EntitlementDetails> details;

    LeaveEntitlements(Id identifier) {
        super(identifier, TYPE);
        this.identifier = identifier;
    }

    @Override
    public void handle(Event event) {

    }

    public Id id() {
        return identifier;
    }

}
