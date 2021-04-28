package io.github.makintsian;

import io.github.makintsian.validator.SchemaValidator;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SchemaValidatorTest {

    private static final String JSON_PATH = "src/test/resources/test.json";
    private static final String XML_PATH = "src/test/resources/test.xml";
    private static final String SCHEMA_PATH = "src/test/resources/schema.yaml";

    @Test
    public void testValidateJsonFromInputStream() throws IOException {
        InputStream schema = FileUtils.openInputStream(new File(SCHEMA_PATH));
        InputStream json = FileUtils.openInputStream(new File(JSON_PATH));
        SchemaValidator schemaValidator = SchemaValidator.newBuilder()
                .withSchema(schema)
                .build();
        schemaValidator.validateJson(json, "json");
    }

    @Test
    public void testValidateJsonFromString() throws IOException {
        String schema = Files.readString(Paths.get(SCHEMA_PATH));
        String json = Files.readString(Paths.get(JSON_PATH));
        SchemaValidator schemaValidator = SchemaValidator.newBuilder()
                .withSchema(schema)
                .build();
        schemaValidator.validateJson(json, "json");
    }

    @Test
    public void testValidateXmlFromInputStream() throws IOException {
        InputStream schema = FileUtils.openInputStream(new File(SCHEMA_PATH));
        InputStream xml = FileUtils.openInputStream(new File(XML_PATH));
        SchemaValidator schemaValidator = SchemaValidator.newBuilder()
                .withSchema(schema)
                .build();
        schemaValidator.validateXml(xml, "xml");
    }

    @Test
    public void testValidateXmlFromString() throws IOException {
        String schema = Files.readString(Paths.get(SCHEMA_PATH));
        String xml = Files.readString(Paths.get(XML_PATH));
        SchemaValidator schemaValidator = SchemaValidator.newBuilder()
                .withSchema(schema)
                .build();
        schemaValidator.validateXml(xml, "xml");
    }
}
