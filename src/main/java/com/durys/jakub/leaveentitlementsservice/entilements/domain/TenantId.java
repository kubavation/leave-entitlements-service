package com.durys.jakub.leaveentitlementsservice.entilements.domain;

import java.util.Objects;
import java.util.UUID;

record TenantId(UUID value) {

    TenantId {
        if (Objects.isNull(value)) {
            throw new RuntimeException("Tenant id cannot be empty");
        }
    }

}
