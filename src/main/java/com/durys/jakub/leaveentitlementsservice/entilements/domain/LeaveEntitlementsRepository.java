package com.durys.jakub.leaveentitlementsservice.entilements.domain;

import java.util.Set;

public interface LeaveEntitlementsRepository {
    LeaveEntitlements load(LeaveEntitlements.Id id);
    Set<LeaveEntitlements> loadAll(TenantId tenantId);
    void save(LeaveEntitlements entitlements);
}
