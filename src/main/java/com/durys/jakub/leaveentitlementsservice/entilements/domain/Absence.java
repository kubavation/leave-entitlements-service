package com.durys.jakub.leaveentitlementsservice.entilements.domain;

import lombok.AccessLevel;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter(AccessLevel.PACKAGE)
class Absence {
    private final UUID id;
    private final LocalDate at;

    Absence(UUID id, LocalDate at) {
        this.id = id;
        this.at = at;
    }
}
