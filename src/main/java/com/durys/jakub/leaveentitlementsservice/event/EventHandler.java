package com.durys.jakub.leaveentitlementsservice.event;

public interface EventHandler<T> {
    void handle(T t);
}
