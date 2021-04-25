package com.github.makintsian.validator;

import com.github.makintsian.paths.ParsePaths;
import com.github.makintsian.exceptions.SchemaValidatorException;
import com.github.makintsian.paths.impl.JsonParsePaths;
import com.github.makintsian.paths.impl.XmlParsePaths;
import org.apache.commons.collections4.MapUtils;
import org.junit.jupiter.api.Assertions;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SchemaValidator {

    private Map<String, List<String>> schema;

    private SchemaValidator() {
    }

    public static SchemaValidator.Builder newBuilder() {
        return new SchemaValidator().new Builder();
    }

    public class Builder {

        private final Yaml yaml;

        private Builder() {
            this.yaml = new Yaml();
        }

        public SchemaValidator.Builder withSchema(InputStream file) {
            SchemaValidator.this.schema = yaml.load(file);
            return this;
        }

        public SchemaValidator.Builder withSchema(String file) {
            SchemaValidator.this.schema = yaml.load(file);
            return this;
        }

        public SchemaValidator build() {
            if (MapUtils.isEmpty(SchemaValidator.this.schema))
                throw new SchemaValidatorException("Schemas is empty");
            return SchemaValidator.this;
        }
    }

    private List<String> getSchema(String name) {
        return Optional.ofNullable(schema.get(name))
                .orElseThrow(() -> new SchemaValidatorException(String.format("Schema '%s' is absent", name)));
    }

    public void validateJson(InputStream json, String name) {
        ParsePaths parsePaths = new JsonParsePaths(json);
        List<String> expected = getSchema(name).stream().sorted().collect(Collectors.toList());
        List<String> actual = parsePaths.getPathsList();
        Assertions.assertEquals(expected, actual, "json schema");
    }

    public void validateJson(String json, String name) {
        ParsePaths parsePaths = new JsonParsePaths(json);
        List<String> expected = getSchema(name).stream().sorted().collect(Collectors.toList());
        List<String> actual = parsePaths.getPathsList();
        Assertions.assertEquals(expected, actual, "json schema");
    }

    public void validateXml(InputStream xml, String name) {
        ParsePaths parsePaths = new XmlParsePaths(xml);
        List<String> expected = getSchema(name).stream().sorted().collect(Collectors.toList());
        List<String> actual = parsePaths.getPathsList();
        Assertions.assertEquals(expected, actual, "xml schema");
    }

    public void validateXml(String xml, String name) {
        ParsePaths parsePaths = new XmlParsePaths(xml);
        List<String> expected = getSchema(name).stream().sorted().collect(Collectors.toList());
        List<String> actual = parsePaths.getPathsList();
        Assertions.assertEquals(expected, actual, "xml schema");
    }
}
