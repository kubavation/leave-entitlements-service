package com.durys.jakub.leaveentitlementsservice.employee.domain;

import com.durys.jakub.leaveentitlementsservice.common.exception.DomainValidationException;

import java.math.BigDecimal;
import java.util.Objects;

record Post(BigDecimal value) {

    public Post {
        if (Objects.isNull(value)) {
            throw new DomainValidationException("Post value cannot be empty");
        }

        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DomainValidationException("Invalid post value");
        }
    }

}
