package com.durys.jakub.leaveentitlementsservice.entilements.domain;

import java.util.Objects;

record AbsenceType(String name) {

    AbsenceType {
        if (Objects.isNull(name)) {
            throw new RuntimeException("Absence name cannot be empty");
        }
    }

}