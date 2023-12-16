package com.durys.jakub.leaveentitlementsservice.entilements.domain;

import com.durys.jakub.leaveentitlementsservice.cqrs.DomainEvent;
import com.durys.jakub.leaveentitlementsservice.ddd.AggregateRoot;
import com.durys.jakub.leaveentitlementsservice.entilements.domain.events.LeaveEntitlementsEvent;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Slf4j
public class LeaveEntitlements extends AggregateRoot<LeaveEntitlementsEvent> {

    private static final String TYPE = "LeaveEntitlement";


    public record Id(AbsenceType absenceType, TenantId tenantId) {

        public Id(String absenceType, UUID tenantId) {
            this(new AbsenceType(absenceType), new TenantId(tenantId));
        }

    }

    public enum State {
        Active, Archived
    }


    private Id identifier;
    private State state;
    private Set<Entitlement> details;


    public LeaveEntitlements(Id identifier) {
        super(identifier, TYPE);
        this.identifier = identifier;
        this.state = State.Active;
        this.details = new HashSet<>();

        apply(new LeaveEntitlementsEvent.LeaveEntitlementsInitialized(identifier));
    }

    @Override
    public void handle(LeaveEntitlementsEvent event) {
        log.info("handling event {}", event);
        switch (event) {
            case LeaveEntitlementsEvent.LeaveEntitlementsGranted granted -> handle(granted);
            case LeaveEntitlementsEvent.LeaveEntitlementsInitialized initialized -> handle(initialized);
            default -> log.warn("Not supported event");
        }
    }

    public void grantEntitlements(LocalDate from, LocalDate to, Integer days) {
        //todo validation

        apply(new LeaveEntitlementsEvent.LeaveEntitlementsGranted(from, to, days));
    }

    public void appendAbsence(LocalDate from, LocalDate to) {

    }


    private void handle(LeaveEntitlementsEvent.LeaveEntitlementsInitialized event) {
        this.identifier = event.identifier();
        this.state = State.Active;
        this.details = new HashSet<>();
    }

    private void handle(LeaveEntitlementsEvent.LeaveEntitlementsGranted event) {
        Entitlement entitlement = new Entitlement(event.from(), event.to(), event.days());
        details.add(entitlement);
    }


    public Id id() {
        return identifier;
    }


    public static class Factory {

        public static LeaveEntitlements create(String absence, UUID tenantId) {
            return new LeaveEntitlements(new Id(absence, tenantId));
        }

    }

}
