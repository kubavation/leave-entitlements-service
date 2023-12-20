package com.durys.jakub.leaveentitlementsservice.entilements.application;

import com.durys.jakub.leaveentitlementsservice.entilements.domain.LeaveEntitlements;
import com.durys.jakub.leaveentitlementsservice.entilements.domain.LeaveEntitlementsRepository;
import com.durys.jakub.leaveentitlementsservice.entilements.domain.command.InitializedLeaveEntitlementsCommand;
import com.durys.jakub.leaveentitlementsservice.entilements.infrastructure.InMemoryLeaveEntitlementsRepository;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LeaveEntitlementsApplicationServiceTest {

    private final LeaveEntitlementsRepository repository = new InMemoryLeaveEntitlementsRepository();
    private final LeaveEntitlementsApplicationService leaveEntitlementsApplicationService = new LeaveEntitlementsApplicationService(repository);

    @Test
    void shouldInitializeLeaveEntitlements() {

        var command = new InitializedLeaveEntitlementsCommand("W", UUID.randomUUID());
        var id = new LeaveEntitlements.Id(command.absence(), command.tenantId());

        leaveEntitlementsApplicationService.handle(command);

        LeaveEntitlements entitlements = repository.load(id);
        assertNotNull(entitlements);
    }
}