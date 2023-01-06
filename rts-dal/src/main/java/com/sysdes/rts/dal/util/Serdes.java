package com.sysdes.rts.dal.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class Serdes {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public <T> Optional<String> toJson(T data) {
        try {
            return Optional.ofNullable(objectMapper.writeValueAsString(data));
        } catch (JsonProcessingException e) {
            log.error("Error while converting toJson {}", data, e);
            return Optional.empty();
        }
    }

    public <T> Optional<T> fromJson(String data, Class<T> clazz) {
        try {
            return Optional.ofNullable(objectMapper.readValue(data, clazz));
        } catch (JsonProcessingException e) {
            log.error("Error while converting toJson {}", data, e);
            return Optional.empty();
        }
    }
}
