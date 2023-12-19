package com.durys.jakub.leaveentitlementsservice.absence.domain;

import java.util.UUID;

public interface AbsenceRepository {
    Absence find(UUID tenantId, String type);
}
