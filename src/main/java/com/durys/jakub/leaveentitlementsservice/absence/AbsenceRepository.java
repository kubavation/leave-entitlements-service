package com.durys.jakub.leaveentitlementsservice.absence;

import java.util.UUID;

public interface AbsenceRepository {
    AbsenceConfiguration find(UUID tenantId, String type);
}
