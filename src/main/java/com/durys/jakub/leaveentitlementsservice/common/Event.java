package com.durys.jakub.leaveentitlementsservice.common;

import java.time.Instant;
import java.util.UUID;

public record Event(UUID id, String aggregateId, String type, Instant at, byte[] data) {}
