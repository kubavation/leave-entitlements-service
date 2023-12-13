package com.durys.jakub.leaveentitlementsservice.entilements.infrastructure;

import com.durys.jakub.leaveentitlementsservice.entilements.domain.LeaveEntitlements;
import com.durys.jakub.leaveentitlementsservice.entilements.domain.LeaveEntitlementsRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryLeaveEntitlementsRepository implements LeaveEntitlementsRepository {

    private static final Map<LeaveEntitlements.Id, LeaveEntitlements> DB = new ConcurrentHashMap<>();

    @Override
    public LeaveEntitlements load(LeaveEntitlements.Id id) {
        return DB.get(id);
    }

    @Override
    public void save(LeaveEntitlements entitlements) {
        DB.put(entitlements.id(), entitlements);
    }
}
