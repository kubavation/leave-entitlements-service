package com.durys.jakub.leaveentitlementsservice.employee;

import java.util.UUID;

public record Employment(UUID tenantId, Post post, EmploymentDate employmentDate, DismissalDate dismissalDate) {
}