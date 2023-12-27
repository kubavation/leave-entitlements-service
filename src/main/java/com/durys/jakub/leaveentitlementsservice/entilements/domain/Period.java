package com.durys.jakub.leaveentitlementsservice.entilements.domain;

import com.durys.jakub.leaveentitlementsservice.common.exception.DomainValidationException;

import java.time.LocalDate;
import java.util.Objects;

record Period(LocalDate from, LocalDate to) {

    Period {
        if (Objects.isNull(from) || Objects.isNull(to)) {
            throw new DomainValidationException("Date cannot be empty");
        }

        if (from.isAfter(to)) {
            throw new DomainValidationException("Invalid period");
        }
    }
}
