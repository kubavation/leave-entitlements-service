package com.durys.jakub.leaveentitlementsservice.entilements.domain.command;

import com.durys.jakub.leaveentitlementsservice.cqrs.Command;

import java.time.LocalDate;
import java.util.UUID;

public record GrantLeaveEntitlementsCommand(String absence, UUID tenantId, LocalDate from, LocalDate to, Integer amount) implements Command {
}
