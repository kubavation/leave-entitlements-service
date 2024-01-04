package com.durys.jakub.leaveentitlementsservice.ddd;

import com.durys.jakub.leaveentitlementsservice.common.exception.DomainValidationException;
import com.durys.jakub.leaveentitlementsservice.cqrs.DomainEvent;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public abstract class EventSourced<T extends DomainEvent> {

    protected Long version;
    protected final List<T> events = new ArrayList<>();

    protected EventSourced() {
        this.version = 0L;
    }

    public abstract void handle(T event);

    public void raise(T event) {
        validate(event);
        handle(event);
        incrementVersion();
    }

    public void apply(T event) {
        raise(event);
        events.add(event);
    }

    protected final void load(List<T> events) {
        events.stream()
                .forEach(this::handle);
    }


    protected void validate(T event) {

        if (Objects.isNull(event)) {
            throw new DomainValidationException("Invalid event");
        }
    }


    protected void incrementVersion() {
        version++;
    }

}
