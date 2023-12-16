package com.durys.jakub.leaveentitlementsservice.ddd;

import com.durys.jakub.leaveentitlementsservice.cqrs.DomainEvent;
import com.durys.jakub.leaveentitlementsservice.es.Event;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public abstract class AggregateRoot {

    protected final Object id;
    protected String type;
    protected Long version;
    protected final List<DomainEvent> events = new ArrayList<>();

    protected AggregateRoot(Object id, String type) {
        this.id = id;
        this.type = type;
        this.version = 0L;
    }

    public abstract void handle(DomainEvent event);

    public void raise(DomainEvent event) {

        validate(event);
        handle(event);
        incrementVersion();
    }

    public void apply(DomainEvent event) {
        raise(event);
        events.add(event);
    }


    protected void validate(DomainEvent event) {

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
