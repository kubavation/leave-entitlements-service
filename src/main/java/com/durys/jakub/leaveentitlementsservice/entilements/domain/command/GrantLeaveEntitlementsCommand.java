package com.durys.jakub.leaveentitlementsservice.entilements.domain.command;

import java.time.LocalDate;
import java.util.UUID;

record GrantLeaveEntitlementsCommand(String absence, UUID tenantId, LocalDate from, LocalDate to, Integer amount) {
}
