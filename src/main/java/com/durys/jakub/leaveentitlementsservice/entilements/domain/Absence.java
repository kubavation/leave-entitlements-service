package com.durys.jakub.leaveentitlementsservice.entilements.domain;

import com.durys.jakub.leaveentitlementsservice.common.exception.DomainValidationException;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;


record Absence(AbsenceId id, LocalDate at) {

    Absence {

        if (Objects.isNull(id)) {
            throw new DomainValidationException("Absence ID cannot be empty");
        }

        if (Objects.isNull(at)) {
            throw new DomainValidationException("Absence date cannot be empty");
        }

    }

    Absence(UUID id, LocalDate at) {
        this(new AbsenceId(id), at);
    }

}

record AbsenceId(UUID value) {

    AbsenceId {
        if (Objects.isNull(value)) {
            throw new DomainValidationException("Absence ID cannot be empty");
        }
    }

}


record AbsenceType(String name) {

    AbsenceType {
        if (Objects.isNull(name)) {
            throw new DomainValidationException("Absence name cannot be empty");
        }
    }

}