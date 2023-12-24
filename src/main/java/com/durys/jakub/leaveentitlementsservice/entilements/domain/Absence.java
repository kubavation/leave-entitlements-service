package com.durys.jakub.leaveentitlementsservice.entilements.domain;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;


record Absence(AbsenceId id, LocalDate at) {

    Absence(UUID id, LocalDate at) {
        this(new AbsenceId(id), at);
    }

}

record AbsenceId(UUID value) {
}


record AbsenceType(String name) {

    AbsenceType {
        if (Objects.isNull(name)) {
            throw new RuntimeException("Absence name cannot be empty");
        }
    }

}