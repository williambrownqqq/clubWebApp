package com.masterswork.account.api.converter;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.masterswork.account.model.enumeration.PersonType;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class StringToPersonTypeEnumConverter implements
        com.fasterxml.jackson.databind.util.Converter<String, PersonType>,
        org.springframework.core.convert.converter.Converter<String, PersonType> {

    @Override
    public PersonType convert(@Nullable String source) {
        try {
            return StringUtils.hasText(source) ?
                    PersonType.valueOf(source.trim().toUpperCase()) : null;
        } catch (Exception e) {
            throw new ConversionFailedException(
                    TypeDescriptor.valueOf(String.class), TypeDescriptor.valueOf(PersonType.class), source, e);
        }
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return typeFactory.constructType(String.class);
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return typeFactory.constructType(PersonType.class);
    }

}