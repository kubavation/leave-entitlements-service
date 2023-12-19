package com.durys.jakub.leaveentitlementsservice.absence.infrastructure;

import com.durys.jakub.leaveentitlementsservice.absence.domain.AbsenceConfiguration;
import com.durys.jakub.leaveentitlementsservice.absence.domain.AbsenceRepository;

import java.util.UUID;

public class InMemoryAbsenceRepository implements AbsenceRepository {

    @Override
    public AbsenceConfiguration find(UUID tenantId, String type) {
        return new AbsenceConfiguration(true, AbsenceConfiguration.Settlement.Days);
    }
}
