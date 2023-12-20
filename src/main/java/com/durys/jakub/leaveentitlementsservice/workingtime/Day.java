package com.durys.jakub.leaveentitlementsservice.workingtime;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Day(LocalDate at, BigDecimal hours) {}
