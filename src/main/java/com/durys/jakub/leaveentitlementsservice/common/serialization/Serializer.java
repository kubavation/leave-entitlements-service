package com.durys.jakub.leaveentitlementsservice.common.serialization;

public interface Serializer {
    byte[] serialize(Object object);
    <T> T deserialize(byte[] bytes, Class<T> clazz);
}
