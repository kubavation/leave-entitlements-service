package com.durys.jakub.leaveentitlementsservice.absence.infrastructure;

import com.durys.jakub.leaveentitlementsservice.absence.domain.Absence;
import com.durys.jakub.leaveentitlementsservice.absence.domain.AbsenceRepository;

import java.util.UUID;

public class InMemoryAbsenceRepository implements AbsenceRepository {

    @Override
    public Absence find(UUID tenantId, String type) {
        return new Absence(true, Absence.Settlement.Days);
    }
}
