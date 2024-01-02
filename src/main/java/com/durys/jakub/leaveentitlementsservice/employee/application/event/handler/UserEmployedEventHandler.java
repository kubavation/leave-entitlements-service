package com.durys.jakub.leaveentitlementsservice.employee.application.event.handler;

import com.durys.jakub.leaveentitlementsservice.employee.application.event.UserEmployed;
import com.durys.jakub.leaveentitlementsservice.employee.domain.Employment;
import com.durys.jakub.leaveentitlementsservice.employee.domain.EmploymentFactory;
import com.durys.jakub.leaveentitlementsservice.employee.domain.EmploymentRepository;

class UserEmployedEventHandler {

    private final EmploymentRepository employmentRepository;

    UserEmployedEventHandler(EmploymentRepository employmentRepository) {
        this.employmentRepository = employmentRepository;
    }


    void handle(UserEmployed event) {

        Employment employment = EmploymentFactory.create(event.tenantId(), event.post(), event.employmentDate());

        employmentRepository.save(employment);
    }

}
