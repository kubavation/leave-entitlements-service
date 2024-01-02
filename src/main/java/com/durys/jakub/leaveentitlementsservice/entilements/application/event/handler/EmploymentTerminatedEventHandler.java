package com.durys.jakub.leaveentitlementsservice.entilements.application.event.handler;

import com.durys.jakub.leaveentitlementsservice.employee.domain.event.EmploymentTerminated;
import com.durys.jakub.leaveentitlementsservice.entilements.domain.LeaveEntitlements;
import com.durys.jakub.leaveentitlementsservice.entilements.domain.LeaveEntitlementsRepository;
import com.durys.jakub.leaveentitlementsservice.event.EventHandler;

import java.util.Set;

class EmploymentTerminatedEventHandler implements EventHandler<EmploymentTerminated> {

   private final LeaveEntitlementsRepository leaveEntitlementsRepository;

    EmploymentTerminatedEventHandler(LeaveEntitlementsRepository leaveEntitlementsRepository) {
        this.leaveEntitlementsRepository = leaveEntitlementsRepository;
    }

    @Override
    public void handle(EmploymentTerminated event) {

        Set<LeaveEntitlements> entitlements = leaveEntitlementsRepository.loadAll(event.tenantId());

        entitlements
                .stream()
                .forEach(entitlement -> {
                    entitlement.terminate(event.at());
                    leaveEntitlementsRepository.save(entitlement);
                });

    }
}
