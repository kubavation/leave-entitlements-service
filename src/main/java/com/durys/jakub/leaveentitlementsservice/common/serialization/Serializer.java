package com.durys.jakub.leaveentitlementsservice.common.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Serializer {

    private static final ObjectMapper mapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    @SneakyThrows
    public static byte[] serialize(Object object) {
        return mapper.writeValueAsBytes(object);
    }

    @SneakyThrows
    public static <T> T deserialize(byte[] bytes, Class<T> clazz) {
        return mapper.readValue(bytes, clazz);
    }

}
