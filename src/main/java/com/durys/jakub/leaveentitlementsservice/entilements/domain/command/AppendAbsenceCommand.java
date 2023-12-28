package com.durys.jakub.leaveentitlementsservice.entilements.domain.command;

import java.time.LocalDate;
import java.util.UUID;

public record AppendAbsenceCommand(LocalDate from, LocalDate to, String absence, UUID tenantId) {
}
