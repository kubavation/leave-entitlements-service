package com.durys.jakub.leaveentitlementsservice.entilements.infrastructure;

import com.durys.jakub.leaveentitlementsservice.entilements.domain.LeaveEntitlementsRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LeaveEntitlementsConfiguration {

    @Bean
    LeaveEntitlementsRepository leaveEntitlementsRepository() {
        return new InMemoryLeaveEntitlementsRepository();
    }
}
