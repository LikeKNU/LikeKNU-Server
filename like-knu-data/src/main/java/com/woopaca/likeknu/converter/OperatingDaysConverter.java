package com.woopaca.likeknu.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.stream.Collectors;

@Converter
public class OperatingDaysConverter implements AttributeConverter<EnumSet<DayOfWeek>, String> {

    private static final String SEPARATOR = ",";

    @Override
    public String convertToDatabaseColumn(EnumSet<DayOfWeek> attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.stream()
                .map(Enum::name)
                .collect(Collectors.joining(SEPARATOR));
    }

    @Override
    public EnumSet<DayOfWeek> convertToEntityAttribute(String dbData) {
        EnumSet<DayOfWeek> dayOfWeeks = EnumSet.noneOf(DayOfWeek.class);
        if (dbData == null || dbData.isEmpty()) {
            return dayOfWeeks;
        }

        Arrays.stream(dbData.split(SEPARATOR))
                .forEach(s -> dayOfWeeks.add(DayOfWeek.valueOf(s)));
        return dayOfWeeks;
    }
}
