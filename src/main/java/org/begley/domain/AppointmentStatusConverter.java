package org.begley.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter
public class AppointmentStatusConverter implements AttributeConverter<AppointmentStatus, String> {
 
    @Override
    public String convertToDatabaseColumn(AppointmentStatus category) {
        if (category == null) {
            return null;
        }
        return category.getCode();
    }
 
    @Override
    public AppointmentStatus convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }
 
        return Stream.of(AppointmentStatus.values())
          .filter(c -> c.getCode().equals(code))
          .findFirst()
          .orElseThrow(IllegalArgumentException::new);
    }
}