package com.durys.jakub.leaveentitlementsservice.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AggregateRoot<T> {

    protected final T id;
    protected String type;
    protected Long version;
    protected final List<Event> events = new ArrayList<>();

    public AggregateRoot(T id, String type) {
        this.id = id;
        this.type = type;
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

        if (Objects.isNull(event) || Objects.equals(event.getAggregateId(), id)) {
            throw new RuntimeException("Invalid event");
        }

    }

}
