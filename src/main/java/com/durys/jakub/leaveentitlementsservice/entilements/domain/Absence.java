package com.durys.jakub.leaveentitlementsservice.entilements.domain;

import java.time.LocalDate;
import java.util.UUID;

record Absence(UUID id, LocalDate at) {
}
