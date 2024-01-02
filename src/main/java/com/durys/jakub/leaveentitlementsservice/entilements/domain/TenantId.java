package com.durys.jakub.leaveentitlementsservice.entilements.domain;

import com.durys.jakub.leaveentitlementsservice.common.exception.DomainValidationException;

import java.util.Objects;
import java.util.UUID;

public record TenantId(UUID value) {

    public TenantId {
        if (Objects.isNull(value)) {
            throw new DomainValidationException("Tenant id cannot be empty");
        }
    }

}
