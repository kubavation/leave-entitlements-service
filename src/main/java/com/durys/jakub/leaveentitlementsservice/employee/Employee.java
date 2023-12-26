package com.durys.jakub.leaveentitlementsservice.employee;

import java.time.LocalDate;
import java.util.UUID;

public record Employee(UUID id, LocalDate employmentDate, LocalDate dismissalDate) {
}