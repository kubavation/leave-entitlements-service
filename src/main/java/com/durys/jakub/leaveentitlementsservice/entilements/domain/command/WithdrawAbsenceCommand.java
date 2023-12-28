package com.durys.jakub.leaveentitlementsservice.entilements.domain.command;

import java.util.UUID;

public record WithdrawAbsenceCommand(UUID absenceId, String absence, UUID tenantId) {
}
