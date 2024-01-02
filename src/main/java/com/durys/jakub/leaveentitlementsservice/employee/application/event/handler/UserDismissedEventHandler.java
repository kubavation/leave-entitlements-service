package com.durys.jakub.leaveentitlementsservice.employee.application.event.handler;

import com.durys.jakub.leaveentitlementsservice.employee.application.event.UserDismissed;
import com.durys.jakub.leaveentitlementsservice.employee.domain.Employment;
import com.durys.jakub.leaveentitlementsservice.employee.domain.EmploymentRepository;
import com.durys.jakub.leaveentitlementsservice.employee.domain.TenantId;
import com.durys.jakub.leaveentitlementsservice.event.EventHandler;

class UserDismissedEventHandler implements EventHandler<UserDismissed> {

    private final EmploymentRepository employmentRepository;

    UserDismissedEventHandler(EmploymentRepository employmentRepository) {
        this.employmentRepository = employmentRepository;
    }


    public void handle(UserDismissed event) {

        Employment employment = employmentRepository.load(new TenantId(event.tenantId()));

        employment.dismiss(event.dismissalDate());

        employmentRepository.save(employment);
    }

}
