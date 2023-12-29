package com.durys.jakub.leaveentitlementsservice.employee.domain;

public record Employment(TenantId tenantId, Post post, EmploymentDate employmentDate, DismissalDate dismissalDate) {
}