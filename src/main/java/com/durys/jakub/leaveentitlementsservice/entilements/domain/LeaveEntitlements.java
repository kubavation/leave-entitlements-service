package com.durys.jakub.leaveentitlementsservice.entilements.domain;

import com.durys.jakub.leaveentitlementsservice.ddd.AggregateRoot;

import com.durys.jakub.leaveentitlementsservice.entilements.domain.events.LeaveEntitlementsEvent;
import com.durys.jakub.leaveentitlementsservice.workingtime.WorkingTimeSchedule;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.durys.jakub.leaveentitlementsservice.entilements.domain.events.LeaveEntitlementsEvent.*;


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
    private Set<Entitlement> entitlements;


    public LeaveEntitlements(Id identifier) {
        super(identifier, TYPE);
        apply(new LeaveEntitlementsInitialized(identifier));
    }

    @Override
    public void handle(LeaveEntitlementsEvent event) {

        log.info("handling event {}", event);

        switch (event) {
            case LeaveEntitlementsGranted granted -> handle(granted);
            case LeaveEntitlementsInitialized initialized -> handle(initialized);
            default -> log.warn("Not supported event");
        }
    }

    public void grantEntitlements(LocalDate from, LocalDate to, Integer days) {
        //todo validation

        apply(new LeaveEntitlementsGranted(from, to, days));
    }

    public void appendAbsence(LocalDate from, LocalDate to, WorkingTimeSchedule workingTimeSchedule) {

        apply(new AbsenceAppended(from, to, workingTimeSchedule.days()));
    }


    private void handle(LeaveEntitlementsInitialized event) {
        this.identifier = event.identifier();
        this.state = State.Active;
        this.entitlements = new HashSet<>();
    }

    private void handle(LeaveEntitlementsGranted event) {
        Entitlement entitlement = new Entitlement(event.from(), event.to(), event.days());
        entitlements.add(entitlement);
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
