package com.durys.jakub.leaveentitlementsservice.entilements.domain;

import com.durys.jakub.leaveentitlementsservice.absence.domain.AbsenceConfiguration;
import com.durys.jakub.leaveentitlementsservice.common.exception.DomainValidationException;
import com.durys.jakub.leaveentitlementsservice.ddd.AggregateRoot;
import com.durys.jakub.leaveentitlementsservice.entilements.domain.events.LeaveEntitlementsEvent;
import com.durys.jakub.leaveentitlementsservice.sharedkernel.TenantId;
import com.durys.jakub.leaveentitlementsservice.workingtime.WorkingTimeSchedule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static com.durys.jakub.leaveentitlementsservice.entilements.domain.events.LeaveEntitlementsEvent.*;


@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LeaveEntitlements extends AggregateRoot<LeaveEntitlementsEvent> {

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
    private Details entitlements;

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


    public LeaveEntitlements(Id identifier) {
        apply(new LeaveEntitlementsInitialized(identifier));
    }

    public void grantEntitlements(LocalDate from, LocalDate to, Integer days) {

        if (entitlements.containsEntitlements(from, to)) {
            throw new DomainValidationException("Entitlements already exists in period");
        }

        apply(new LeaveEntitlementsGranted(identifier, from, to, days));
    }

    public void appendAbsence(WorkingTimeSchedule schedule, AbsenceConfiguration absence) {

        if (entitlements.entitlementsNotRegistered(schedule.from(), schedule.to())) {
            throw new DomainValidationException("Entitlements not registered");
        }

        entitlements.validateAmount(schedule, absence);

        apply(new AbsenceAppended(identifier, UUID.randomUUID(), schedule.from(), schedule.to(), schedule.numberOfWorkingDays()));
    }

    public void withdrawAbsence(UUID absenceId) {
        apply(new AbsenceWithdrawed(identifier, absenceId));
    }


    private void handle(LeaveEntitlementsInitialized event) {
        this.identifier = event.identifier();
        this.state = State.Active;
        this.entitlements = new Details();
    }

    private void handle(LeaveEntitlementsGranted event) {
        Entitlement entitlement = new Entitlement(UUID.randomUUID(), event.from(), event.to(), event.days());
        entitlements.add(entitlement);
    }

    private void handle(AbsenceAppended event) {

        Stream.iterate(event.from(), date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(event.from(), event.to()) + 1)
                .forEach(date -> entitlements.appendAbsence(new Absence(event.absenceId(), date)));
    }

    private void handle(AbsenceWithdrawed event) {
        entitlements.withdrawAbsence(new AbsenceId(event.absenceId()));
    }


    public Id id() {
        return identifier;
    }

    public static LeaveEntitlements recreate(List<LeaveEntitlementsEvent> events) {

        var leaveEntitlement = new LeaveEntitlements();

        leaveEntitlement.load(events);

        return leaveEntitlement;
    }


}
