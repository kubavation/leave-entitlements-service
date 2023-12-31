package com.durys.jakub.leaveentitlementsservice.entilements.infrastructure;

import com.durys.jakub.leaveentitlementsservice.entilements.domain.LeaveEntitlements;
import com.durys.jakub.leaveentitlementsservice.entilements.domain.LeaveEntitlementsFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.durys.jakub.leaveentitlementsservice.entilements.infrastructure.InMemoryLeaveEntitlementsRepository.DB;
import static org.junit.jupiter.api.Assertions.*;


class InMemoryLeaveEntitlementsRepositoryTest {

    InMemoryLeaveEntitlementsRepository repository = new InMemoryLeaveEntitlementsRepository();

    @BeforeEach
    void clear() {
        DB.clear();
    }

    @Test
    void shouldSaveLeaveEntitlements() {

        UUID tenantId = UUID.randomUUID();
        String absence = "W";
        LeaveEntitlements entitlements = LeaveEntitlementsFactory.create(absence, tenantId);

        repository.save(entitlements);

        assertFalse(DB.get(new LeaveEntitlements.Id(absence, tenantId)).isEmpty());
    }

    @Test
    void shouldLoadLeaveEntitlements() {

        UUID tenantId = UUID.randomUUID();
        String absence = "W";
        LeaveEntitlements entitlements = LeaveEntitlementsFactory.create(absence, tenantId);
        repository.save(entitlements);

        LeaveEntitlements leaveEntitlements = repository.load(new LeaveEntitlements.Id(absence, tenantId));

        assertNotNull(leaveEntitlements);
        assertTrue(leaveEntitlements.getPendingEvents().isEmpty());
    }


}