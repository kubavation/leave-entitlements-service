package com.durys.jakub.leaveentitlementsservice.ddd;

import com.durys.jakub.leaveentitlementsservice.cqrs.DomainEvent;
import com.durys.jakub.leaveentitlementsservice.es.Event;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public abstract class AggregateRoot<T extends DomainEvent> {

    protected final Object id;
    protected String type;
    protected Long version;
    protected final List<T> events = new ArrayList<>();

    protected AggregateRoot(Object id, String type) {
        this.id = id;
        this.type = type;
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

        if (Objects.isNull(event)) {//|| !Objects.equals(event.getAggregateId(), id)) {
            throw new RuntimeException("Invalid event");
        }

    }

    protected Event createEvent(Class<? extends DomainEvent> eventClass, byte[] data) {
        return new Event(id, eventClass.getSimpleName(), data);
    }

    protected void incrementVersion() {
        version++;
    }

}
