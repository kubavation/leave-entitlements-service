package com.durys.jakub.leaveentitlementsservice.entilements.domain.events;

import com.durys.jakub.leaveentitlementsservice.cqrs.DomainEvent;

import java.time.LocalDate;

public record LeaveEntitlementsGranted(LocalDate from, LocalDate to, Integer days) implements DomainEvent {
}
