package io.github.makintsian.validator;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.makintsian.paths.ParsePaths;
import io.github.makintsian.exceptions.SchemaValidatorException;
import io.github.makintsian.paths.impl.JsonParsePaths;
import io.github.makintsian.paths.impl.XmlParsePaths;
import io.github.makintsian.utils.YamlReader;
import org.apache.commons.collections4.MapUtils;
import org.junit.jupiter.api.Assertions;

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

        private final YamlReader yamlReader;
        private final TypeReference<Map<String, List<String>>> typeRef;

        private Builder() {
            this.yamlReader = new YamlReader();
            this.typeRef = new TypeReference<>() {
            };
        }

        public SchemaValidator.Builder withSchema(InputStream file) {
            SchemaValidator.this.schema = yamlReader.readFile(file, typeRef);
            return this;
        }

        public SchemaValidator.Builder withSchema(String file) {
            SchemaValidator.this.schema = yamlReader.readFile(file, typeRef);
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
