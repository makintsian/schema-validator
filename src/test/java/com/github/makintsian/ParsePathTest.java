package com.github.makintsian;

import com.github.makintsian.paths.ParsePaths;
import com.github.makintsian.paths.impl.XmlParsePaths;
import com.github.makintsian.paths.impl.JsonParsePaths;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class ParsePathTest {

    private static final String JSON_PATH = "src/test/resources/test.json";
    private static final String XML_PATH = "src/test/resources/test.xml";
    private static final List<String> EXPECTED_JSON_RESULT = Arrays.asList("address.city", "address.postalCode",
            "address.streetAddress", "age", "devices[*]", "firstName", "languages[*]", "lastName",
            "phoneNumbers[*].number", "phoneNumbers[*].type", "tax");
    private static final List<String> EXPECTED_XML_RESULT = Arrays.asList("breakfast_menu/food/calories",
            "breakfast_menu/food/description", "breakfast_menu/food/name", "breakfast_menu/food/price");

    @Test
    public void testGetJsonPathsListFromInputStream() throws IOException {
        InputStream json = FileUtils.openInputStream(new File(JSON_PATH));
        ParsePaths parsePaths = new JsonParsePaths(json);
        List<String> actual = parsePaths.getPathsList();
        Assertions.assertEquals(EXPECTED_JSON_RESULT, actual);
    }

    @Test
    public void testGetJsonPathsListFromString() throws IOException {
        String json = Files.readString(Paths.get(JSON_PATH));
        ParsePaths parsePaths = new JsonParsePaths(json);
        List<String> actual = parsePaths.getPathsList();
        Assertions.assertEquals(EXPECTED_JSON_RESULT, actual);
    }

    @Test
    public void testGetXmlPathsListFromInputStream() throws IOException {
        InputStream xml = FileUtils.openInputStream(new File(XML_PATH));
        ParsePaths parsePaths = new XmlParsePaths(xml);
        List<String> actual = parsePaths.getPathsList();
        Assertions.assertEquals(EXPECTED_XML_RESULT, actual);
    }

    @Test
    public void testGetXmlPathsListFromString() throws IOException {
        String xml = Files.readString(Paths.get(XML_PATH));
        ParsePaths parsePaths = new XmlParsePaths(xml);
        List<String> actual = parsePaths.getPathsList();
        Assertions.assertEquals(EXPECTED_XML_RESULT, actual);
    }
}
