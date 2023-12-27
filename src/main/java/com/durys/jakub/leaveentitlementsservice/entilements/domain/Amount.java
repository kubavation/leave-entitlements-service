package com.durys.jakub.leaveentitlementsservice.entilements.domain;

import com.durys.jakub.leaveentitlementsservice.common.exception.DomainValidationException;

import java.util.Objects;

record Amount(Integer days) {

    Amount {

        if (Objects.isNull(days)) {
            throw new DomainValidationException("Amount value cannot be empty");
        }

        if (days < 0) {
            throw new DomainValidationException("Invalid amount value");
        }

    }

}
