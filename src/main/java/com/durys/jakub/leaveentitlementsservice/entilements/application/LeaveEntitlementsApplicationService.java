package com.durys.jakub.leaveentitlementsservice.entilements.application;

import com.durys.jakub.leaveentitlementsservice.absence.domain.AbsenceConfiguration;
import com.durys.jakub.leaveentitlementsservice.absence.domain.AbsenceRepository;
import com.durys.jakub.leaveentitlementsservice.entilements.domain.LeaveEntitlements;
import com.durys.jakub.leaveentitlementsservice.entilements.domain.LeaveEntitlementsRepository;
import com.durys.jakub.leaveentitlementsservice.entilements.domain.command.AppendAbsenceCommand;
import com.durys.jakub.leaveentitlementsservice.entilements.domain.command.GrantLeaveEntitlementsCommand;
import com.durys.jakub.leaveentitlementsservice.entilements.domain.command.InitializedLeaveEntitlementsCommand;
import com.durys.jakub.leaveentitlementsservice.entilements.domain.command.WithdrawAbsenceCommand;
import com.durys.jakub.leaveentitlementsservice.workingtime.WorkingTimeSchedule;
import com.durys.jakub.leaveentitlementsservice.workingtime.WorkingTimeScheduleRepository;
import org.springframework.stereotype.Component;

@Component
class LeaveEntitlementsApplicationService {

    private final LeaveEntitlementsRepository leaveEntitlementsRepository;
    private final AbsenceRepository absenceRepository;
    private final WorkingTimeScheduleRepository workingTimeScheduleRepository;

    LeaveEntitlementsApplicationService(LeaveEntitlementsRepository leaveEntitlementsRepository,
                                        AbsenceRepository absenceRepository,
                                        WorkingTimeScheduleRepository workingTimeScheduleRepository) {

        this.leaveEntitlementsRepository = leaveEntitlementsRepository;
        this.absenceRepository = absenceRepository;
        this.workingTimeScheduleRepository = workingTimeScheduleRepository;
    }

    public void handle(InitializedLeaveEntitlementsCommand command) {

        LeaveEntitlements entitlements = new LeaveEntitlements(new LeaveEntitlements.Id(command.absence(), command.tenantId()));

        leaveEntitlementsRepository.save(entitlements);
    }

    public void handle(GrantLeaveEntitlementsCommand command) {

        LeaveEntitlements entitlements = leaveEntitlementsRepository.load(new LeaveEntitlements.Id(command.absence(), command.tenantId()));

        entitlements.grantEntitlements(command.from(), command.to(), command.amount());

        leaveEntitlementsRepository.save(entitlements);
    }

    public void handle(AppendAbsenceCommand command) {

        WorkingTimeSchedule schedule = workingTimeScheduleRepository.load(command.tenantId(), command.from(), command.to());
        AbsenceConfiguration absenceConfiguration = absenceRepository.load(command.tenantId(), command.absence());

        LeaveEntitlements entitlements = leaveEntitlementsRepository.load(new LeaveEntitlements.Id(command.absence(), command.tenantId()));

        entitlements.appendAbsence(schedule, absenceConfiguration);

        leaveEntitlementsRepository.save(entitlements);
    }

    public void handle(WithdrawAbsenceCommand command) {

        LeaveEntitlements entitlements = leaveEntitlementsRepository.load(new LeaveEntitlements.Id(command.absence(), command.tenantId()));

        entitlements.withdrawAbsence(command.absenceId());

        leaveEntitlementsRepository.save(entitlements);
    }
}
