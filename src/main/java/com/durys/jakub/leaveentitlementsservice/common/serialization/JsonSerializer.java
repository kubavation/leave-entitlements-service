package com.durys.jakub.leaveentitlementsservice.common.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
class JsonSerializer implements Serializer {

    private static final ObjectMapper mapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    @Override
    @SneakyThrows
    public byte[] serialize(Object object) {
        return mapper.writeValueAsBytes(object);
    }

    @Override
    @SneakyThrows
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        return mapper.readValue(bytes, clazz);
    }
}
