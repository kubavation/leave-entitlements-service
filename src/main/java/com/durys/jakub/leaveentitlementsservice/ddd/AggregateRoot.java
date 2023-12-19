package com.durys.jakub.leaveentitlementsservice.ddd;

import com.durys.jakub.leaveentitlementsservice.cqrs.DomainEvent;
import com.durys.jakub.leaveentitlementsservice.es.Event;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public abstract class AggregateRoot<T extends DomainEvent> {

    protected Long version;
    protected final List<T> events = new ArrayList<>();

    protected AggregateRoot() {
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


    protected void validate(T event) {

        if (Objects.isNull(event)) {
            throw new RuntimeException("Invalid event");
        }

    }


    protected void incrementVersion() {
        version++;
    }

}
