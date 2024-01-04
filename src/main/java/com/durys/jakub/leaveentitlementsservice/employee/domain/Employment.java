package com.durys.jakub.leaveentitlementsservice.employee.domain;

import com.durys.jakub.leaveentitlementsservice.sharedkernel.TenantId;

import java.time.LocalDate;

public class Employment {
    private final TenantId tenantId;
    private final Post post;
    private final EmploymentDate employmentDate;
    private DismissalDate dismissalDate;

    public Employment(TenantId tenantId, Post post, EmploymentDate employmentDate, DismissalDate dismissalDate) {
        this.tenantId = tenantId;
        this.post = post;
        this.employmentDate = employmentDate;
        this.dismissalDate = dismissalDate;
    }

    public void dismiss(LocalDate dismissalDate) {
        this.dismissalDate = new DismissalDate(dismissalDate);
    }

    public TenantId tenantId() {
        return tenantId;
    }

    public Post post() {
        return post;
    }

    public EmploymentDate employmentDate() {
        return employmentDate;
    }

    public DismissalDate dismissalDate() {
        return dismissalDate;
    }

}