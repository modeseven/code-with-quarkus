package org.begley.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter
public class AppointmentTypeConverter implements AttributeConverter<AppointmentType, String> {
 
    @Override
    public String convertToDatabaseColumn(AppointmentType category) {
        if (category == null) {
            return null;
        }
        return category.getCode();
    }
 
    @Override
    public AppointmentType convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }
 
        return Stream.of(AppointmentType.values())
          .filter(c -> c.getCode().equals(code))
          .findFirst()
          .orElseThrow(IllegalArgumentException::new);
    }
}