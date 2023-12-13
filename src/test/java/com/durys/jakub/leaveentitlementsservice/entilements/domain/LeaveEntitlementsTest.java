package com.durys.jakub.leaveentitlementsservice.entilements.domain;

import com.durys.jakub.leaveentitlementsservice.common.serialization.Serializer;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LeaveEntitlementsTest {

    @Test
    void shouldInitializeLeaveEntitlements() {

        LeaveEntitlements entitlements = LeaveEntitlements.Factory.create("W", UUID.randomUUID());

        assertFalse(entitlements.getEvents().isEmpty());
    }


}