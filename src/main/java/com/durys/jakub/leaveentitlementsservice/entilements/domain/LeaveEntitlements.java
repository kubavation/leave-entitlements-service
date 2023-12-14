package com.durys.jakub.leaveentitlementsservice.entilements.domain;

import com.durys.jakub.leaveentitlementsservice.ddd.AggregateRoot;
import com.durys.jakub.leaveentitlementsservice.entilements.domain.events.LeaveEntitlementsGranted;
import com.durys.jakub.leaveentitlementsservice.entilements.domain.events.LeaveEntitlementsInitialized;
import com.durys.jakub.leaveentitlementsservice.es.Event;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.durys.jakub.leaveentitlementsservice.common.serialization.Serializer.serialize;

@Slf4j
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
    private Set<Entitlement> details;


    public LeaveEntitlements(Id identifier) {
        super(identifier, TYPE);
        this.identifier = identifier;
        this.state = State.Active;
        this.details = new HashSet<>();

        var leaveEntitlementsInitialized = new LeaveEntitlementsInitialized(identifier);

        apply(
            createEvent(LeaveEntitlementsInitialized.class, serialize(leaveEntitlementsInitialized))
        );
    }

    @Override
    public void handle(Event event) {
        log.info("handling event {}", event);
    }

    public Id id() {
        return identifier;
    }

    public void grantEntitlements(LocalDate from, LocalDate to, Integer days) {

        //todo validation

        var leaveEntitlementsGranted = new LeaveEntitlementsGranted(from, to, days);

        apply(
            createEvent(LeaveEntitlementsGranted.class, serialize(leaveEntitlementsGranted))
        );
    }


    public static class Factory {

        public static LeaveEntitlements create(String absence, UUID tenantId) {
            return new LeaveEntitlements(new Id(absence, tenantId));
        }
    }

}
