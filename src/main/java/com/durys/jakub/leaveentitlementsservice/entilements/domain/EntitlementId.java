package com.durys.jakub.leaveentitlementsservice.entilements.domain;

import com.durys.jakub.leaveentitlementsservice.common.exception.DomainValidationException;

import java.util.Objects;
import java.util.UUID;

record EntitlementId(UUID value) {

    EntitlementId {

        if (Objects.isNull(value)) {
            throw new DomainValidationException("Entitlement ID cannot be empty");
        }
    }
}
