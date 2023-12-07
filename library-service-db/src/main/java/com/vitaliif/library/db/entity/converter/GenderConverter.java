package com.vitaliif.library.db.entity.converter;

import com.vitaliif.library.db.entity.Gender;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class GenderConverter implements AttributeConverter<Gender, String> {
    @Override
    public String convertToDatabaseColumn(Gender gender) {
        return gender.getGenderSymbol();
    }

    @Override
    public Gender convertToEntityAttribute(String dbData) {
        return Gender.getFromSymbol(dbData);
    }
}

