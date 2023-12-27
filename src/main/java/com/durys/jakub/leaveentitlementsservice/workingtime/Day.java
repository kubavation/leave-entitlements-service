package com.durys.jakub.leaveentitlementsservice.workingtime;

import com.durys.jakub.leaveentitlementsservice.common.exception.DomainValidationException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public record Day(LocalDate at, BigDecimal hours) {

    public Day {
        if (Objects.isNull(at)) {
           throw new DomainValidationException("Date cannot be empty");
        }

        if (Objects.nonNull(hours) && hours.compareTo(BigDecimal.ZERO) < 0) {
            throw new DomainValidationException("Invalid hours value");
        }
    }

}
