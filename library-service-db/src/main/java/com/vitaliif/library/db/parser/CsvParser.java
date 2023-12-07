package com.vitaliif.library.db.parser;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class CsvParser {

    public <T> List<T> parseCsvReflection(String filePath, Class<T> clazz) {
        List<T> items = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(prepareFileReader(filePath))) {
            String[] headers = csvReader.readNext()[0].split("\t"); // Assuming the first row is headers

            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                String[] values = String.join("", nextRecord).split("\t");
                T item = createDto(values, headers, clazz);
                items.add(item);
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }

        return items;
    }

    private <T> T createDto(String[] values, String[] headers, Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getConstructor();

            T dto = constructor.newInstance();

            for (int i = 0; i < values.length; i++) {
                String header = headers[i];
                String value = values[i];

                Field field = clazz.getDeclaredField(header.toLowerCase());
                field.setAccessible(true);

                field.set(dto, value);
            }
            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Error creating DTO", e);
        }
    }

    public <T> List<T> parse(Class<T> tClass, String filePath) {
        final CsvToBean<T> csvBean = prepareCsvBean(tClass, filePath);
        return csvBean.parse();
    }

    private  <T> CsvToBean<T> prepareCsvBean(Class<T> mappedClass, String filePath) {

        final InputStreamReader fileReader = prepareFileReader(filePath);
        return new CsvToBeanBuilder<T>(fileReader).withType(mappedClass).withIgnoreLeadingWhiteSpace(true).build();
    }

    private InputStreamReader prepareFileReader(String filePath) {
            return new InputStreamReader(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(filePath)));
    }
}
