package com.durys.jakub.leaveentitlementsservice.entilements.domain.events;

import java.util.UUID;

record LeaveEntitlementsInitialized(String absence, UUID tenantId) {
}
