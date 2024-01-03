package com.durys.jakub.leaveentitlementsservice.entilements.domain.events;

import com.durys.jakub.leaveentitlementsservice.cqrs.DomainEvent;
import com.durys.jakub.leaveentitlementsservice.entilements.domain.LeaveEntitlements;

import java.time.LocalDate;
import java.util.UUID;

public sealed interface LeaveEntitlementsEvent extends DomainEvent {

    record AbsenceAppended(LeaveEntitlements.Id id, UUID absenceId, LocalDate from, LocalDate to, Long days) implements LeaveEntitlementsEvent {

        @Override
        public Object aggregateId() {
            return id;
        }
    }

    record AbsenceWithdrawed(LeaveEntitlements.Id id, UUID absenceId) implements LeaveEntitlementsEvent {

        @Override
        public Object aggregateId() {
            return id;
        }
    }

    record LeaveEntitlementsGranted(LeaveEntitlements.Id id, LocalDate from, LocalDate to, Integer days) implements LeaveEntitlementsEvent {

        @Override
        public Object aggregateId() {
            return id;
        }
    }

    record LeaveEntitlementsInitialized(LeaveEntitlements.Id identifier) implements LeaveEntitlementsEvent {

        @Override
        public Object aggregateId() {
            return identifier;
        }
    }

    record LeaveEntitlementsTerminated(LeaveEntitlements.Id identifier, LocalDate at) implements LeaveEntitlementsEvent {

        @Override
        public Object aggregateId() {
            return identifier;
        }
    }

}
