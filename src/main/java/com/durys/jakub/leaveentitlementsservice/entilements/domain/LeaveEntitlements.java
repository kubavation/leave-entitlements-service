package com.durys.jakub.leaveentitlementsservice.entilements.domain;

import com.durys.jakub.leaveentitlementsservice.common.exception.DomainValidationException;
import com.durys.jakub.leaveentitlementsservice.ddd.AggregateRoot;
import com.durys.jakub.leaveentitlementsservice.entilements.domain.events.LeaveEntitlementsEvent;
import com.durys.jakub.leaveentitlementsservice.workingtime.WorkingTimeSchedule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

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
            case AbsenceAppended absenceAppended -> handle(absenceAppended);
            case AbsenceWithdrawed absenceWithdrawed -> handle(absenceWithdrawed);
            default -> log.warn("Not supported event");
        }
    }

    public void grantEntitlements(LocalDate from, LocalDate to, Integer days) {

        if (containsEntitlements(from, to)) {
            throw new DomainValidationException("Entitlements already exists in period");
        }

        apply(new LeaveEntitlementsGranted(identifier, from, to, days));
    }

    public void appendAbsence(LocalDate from, LocalDate to, WorkingTimeSchedule workingTimeSchedule) {

        if (entitlementsNotRegistered(from, to)) {
            throw new DomainValidationException("Entitlements not registered");
        }

        //todo days validation

        apply(new AbsenceAppended(identifier, from, to, workingTimeSchedule.days()));
    }

    public void withdrawAbsence(LocalDate from, LocalDate to) {

        apply(new AbsenceWithdrawed(identifier, from, to));
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

    private void handle(AbsenceAppended event) {

        UUID absenceId = UUID.randomUUID();

        Stream.iterate(event.from(), date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(event.from(), event.to()) + 1)
                .forEach(date -> {
                    Entitlement entitlement = findEntitlement(date)
                            .orElseThrow(RuntimeException::new);
                    entitlement.addAbsence(new Absence(absenceId, date));
                });
    }

    private void handle(AbsenceWithdrawed event) {
        //todo
    }


    public Id id() {
        return identifier;
    }

    private Optional<Entitlement> findEntitlement(LocalDate date) {
        return entitlements.stream()
            .filter(entitlement -> !date.isBefore(entitlement.getPeriod().from()) && !date.isAfter(entitlement.getPeriod().to()))
            .findFirst();
    }


    private boolean entitlementsNotRegistered(LocalDate from, LocalDate to) {
        return Stream.iterate(from, date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(from, to) + 1)
                .map(this::findEntitlement)
                .anyMatch(Optional::isEmpty);
    }


    private boolean containsEntitlements(LocalDate from, LocalDate to) {
        return entitlements.stream()
                .map(Entitlement::getPeriod)
                .anyMatch(period -> !from.isBefore(period.from()) && !to.isAfter(period.to()));
    }


    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Factory {

        public static LeaveEntitlements create(String absence, UUID tenantId) {
            return new LeaveEntitlements(new Id(absence, tenantId));
        }

    }

}
