package com.durys.jakub.leaveentitlementsservice.employee;

public record Employment(TenantId tenantId, Post post, EmploymentDate employmentDate, DismissalDate dismissalDate) {
}