package com.durys.jakub.leaveentitlementsservice.entilements.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LeaveEntitlementsFactory {


    public static LeaveEntitlements create(String absence, UUID tenantId) {
        return new LeaveEntitlements(new LeaveEntitlements.Id(absence, tenantId));
    }

}
