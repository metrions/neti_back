package com.example.neti_back.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Converter
public class ListToIntegerConverter implements AttributeConverter<List<Integer>, String> {
    @Override
    public String convertToDatabaseColumn(List<Integer> attribute) {
        return attribute == null ? null : String.join(",", attribute.stream().map(x -> x.toString()).toList());
    }

    @Override
    public List<Integer> convertToEntityAttribute(String dbData) {
        if (dbData.isEmpty()) return new ArrayList<>();
        return dbData == null ? Collections.emptyList() : Arrays.stream(dbData.split(",")).map(Integer::parseInt).toList();
    }
}
