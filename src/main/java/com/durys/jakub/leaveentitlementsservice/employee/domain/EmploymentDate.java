package com.durys.jakub.leaveentitlementsservice.employee.domain;

import com.durys.jakub.leaveentitlementsservice.common.exception.DomainValidationException;

import java.time.LocalDate;
import java.util.Objects;

record EmploymentDate(LocalDate value) {

    public EmploymentDate {
        if (Objects.isNull(value)) {
            throw new DomainValidationException("Employment date cannot be empty");
        }
    }

}
