package com.example.neti_back.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Converter
public class MapToStringConverter implements AttributeConverter<Map<Integer, String>, String> {

    @Override
    public String convertToDatabaseColumn(Map<Integer, String> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return null;
        }
        return attribute.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining(","));
    }

    @Override
    public Map<Integer, String> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return Collections.emptyMap();
        }
        return Arrays.stream(dbData.split(","))
                .map(entry -> entry.split("="))
                .collect(Collectors.toMap(
                        entry -> Integer.parseInt(entry[0]),
                        entry -> entry[1]
                ));
    }
}
