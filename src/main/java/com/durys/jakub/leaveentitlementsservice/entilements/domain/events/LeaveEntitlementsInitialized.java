package com.durys.jakub.leaveentitlementsservice.entilements.domain.events;

import com.durys.jakub.leaveentitlementsservice.cqrs.DomainEvent;
import com.durys.jakub.leaveentitlementsservice.entilements.domain.LeaveEntitlements;

public record LeaveEntitlementsInitialized(LeaveEntitlements.Id identifier) implements DomainEvent {}
