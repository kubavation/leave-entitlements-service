package com.durys.jakub.leaveentitlementsservice.entilements.domain.events;

import com.durys.jakub.leaveentitlementsservice.cqrs.DomainEvent;
import com.durys.jakub.leaveentitlementsservice.entilements.domain.LeaveEntitlements;

import java.time.LocalDate;

public sealed interface LeaveEntitlementsEvent extends DomainEvent {

    record AbsenceAppended(LocalDate from, LocalDate to, Long days) implements LeaveEntitlementsEvent {}

    record AbsenceWithdrawed(LocalDate from, LocalDate to) implements LeaveEntitlementsEvent {}

    record LeaveEntitlementsGranted(LocalDate from, LocalDate to, Integer days) implements LeaveEntitlementsEvent {}

    record LeaveEntitlementsInitialized(LeaveEntitlements.Id identifier) implements LeaveEntitlementsEvent {}

}
