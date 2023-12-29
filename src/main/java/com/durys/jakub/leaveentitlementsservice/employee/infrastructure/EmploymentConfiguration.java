package com.durys.jakub.leaveentitlementsservice.employee.infrastructure;

import com.durys.jakub.leaveentitlementsservice.employee.domain.EmploymentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class EmploymentConfiguration {

    @Bean
    EmploymentRepository employmentRepository() {
        return new InMemoryEmploymentRepository();
    }

}
