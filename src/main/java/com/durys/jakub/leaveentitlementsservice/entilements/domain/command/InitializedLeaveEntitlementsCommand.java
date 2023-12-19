package com.durys.jakub.leaveentitlementsservice.entilements.domain.command;

import com.durys.jakub.leaveentitlementsservice.cqrs.Command;

import java.time.LocalDate;
import java.util.UUID;

public record InitializedLeaveEntitlementsCommand(String absence, UUID tenantId) implements Command {
}
