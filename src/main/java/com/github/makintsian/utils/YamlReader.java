package com.github.makintsian.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.makintsian.exceptions.SchemaValidatorException;

import java.io.IOException;
import java.io.InputStream;

public class YamlReader {

    private final ObjectMapper objectMapper;

    public YamlReader() {
        this.objectMapper = new ObjectMapper(new YAMLFactory());
    }

    public <T> T readFile(InputStream file, TypeReference<T> typeRef) {
        try {
            return objectMapper.readValue(file, typeRef);
        } catch (IOException ex) {
            throw new SchemaValidatorException("Yaml file is incorrect", ex);
        }
    }

    public <T> T readFile(String file, TypeReference<T> typeRef) {
        try {
            return objectMapper.readValue(file, typeRef);
        } catch (IOException ex) {
            throw new SchemaValidatorException("Yaml file is incorrect", ex);
        }
    }
}
