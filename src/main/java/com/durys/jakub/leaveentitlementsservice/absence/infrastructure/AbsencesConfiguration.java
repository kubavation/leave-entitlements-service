package com.durys.jakub.leaveentitlementsservice.absence.infrastructure;

import com.durys.jakub.leaveentitlementsservice.absence.domain.AbsenceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AbsencesConfiguration {

    @Bean
    AbsenceRepository absenceRepository() {
        return new InMemoryAbsenceRepository();
    }

}


