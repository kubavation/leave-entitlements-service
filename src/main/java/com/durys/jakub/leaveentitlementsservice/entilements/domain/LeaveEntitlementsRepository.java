package com.durys.jakub.leaveentitlementsservice.entilements.domain;

public interface LeaveEntitlementsRepository {
    LeaveEntitlements load(LeaveEntitlements.Id id);
    void save(LeaveEntitlements entitlements);
}
