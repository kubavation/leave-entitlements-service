package com.durys.jakub.leaveentitlementsservice.absence.domain;

import java.util.UUID;

public interface AbsenceRepository {
    AbsenceConfiguration load(UUID tenantId, String type);
}
