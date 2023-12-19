package com.durys.jakub.leaveentitlementsservice.entilements.application;

import com.durys.jakub.leaveentitlementsservice.entilements.domain.LeaveEntitlements;
import com.durys.jakub.leaveentitlementsservice.entilements.domain.LeaveEntitlementsRepository;
import com.durys.jakub.leaveentitlementsservice.entilements.domain.command.InitializedLeaveEntitlementsCommand;
import org.springframework.stereotype.Component;

@Component
class LeaveEntitlementsApplicationService {

    private final LeaveEntitlementsRepository leaveEntitlementsRepository;

    LeaveEntitlementsApplicationService(LeaveEntitlementsRepository leaveEntitlementsRepository) {
        this.leaveEntitlementsRepository = leaveEntitlementsRepository;
    }


    public void handle(InitializedLeaveEntitlementsCommand command) {

        LeaveEntitlements entitlements = new LeaveEntitlements(new LeaveEntitlements.Id(command.absence(), command.tenantId()));

        leaveEntitlementsRepository.save(entitlements);
    }
}
