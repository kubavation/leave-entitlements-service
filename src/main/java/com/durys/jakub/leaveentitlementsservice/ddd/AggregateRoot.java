package com.durys.jakub.leaveentitlementsservice.ddd;

import com.durys.jakub.leaveentitlementsservice.cqrs.DomainEvent;
import com.durys.jakub.leaveentitlementsservice.es.Event;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public abstract class AggregateRoot {

    protected final Object id;
    protected String type;
    protected Long version;
    protected final List<Event> events = new ArrayList<>();

    protected AggregateRoot(Object id, String type) {
        this.id = id;
        this.type = type;
        this.version = 0L;
    }

    public abstract void handle(Event event);

    public void load(List<Event> events) {
        events.forEach(this::raise);
    }

    public void raise(Event event) {

        validate(event);
        event.setType(type);

        handle(event);
        version++;
    }

    public void apply(Event event) {
        raise(event);
        events.add(event);
    }


    protected void validate(Event event) {

        if (Objects.isNull(event) || !Objects.equals(event.getAggregateId(), id)) {
            throw new RuntimeException("Invalid event");
        }

    }

    protected Event createEvent(Class<? extends DomainEvent> eventClass, byte[] data) {
        return new Event(id, eventClass.getSimpleName(), data);
    }

}
