package io.github.makintsian.paths.impl;

import io.github.makintsian.exceptions.SchemaValidatorException;
import io.github.makintsian.paths.ParsePaths;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class JsonParsePaths implements ParsePaths {

    private final List<String> paths;

    public JsonParsePaths(InputStream inputStream) {
        this.paths = new ArrayList<>();
        String json;
        try {
            json = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
        } catch (IOException ex) {
            throw new SchemaValidatorException("Cannot convert to string", ex);
        }
        parseJson(json);
    }

    public JsonParsePaths(String json) {
        this.paths = new ArrayList<>();
        parseJson(json);
    }

    /**
     * @return List with json paths
     */
    public List<String> getPathsList() {
        return removeDuplicates(paths);
    }

    /**
     * @param json String
     */
    private void parseJson(String json) {
        JsonElement jsonTree = JsonParser.parseString(json);
        if (!jsonTree.isJsonObject() && !jsonTree.isJsonArray())
            throw new SchemaValidatorException("Json is not valid");
        writeAndSortJson(jsonTree);
    }

    /**
     * @param jsonTree JsonElement
     */
    private void writeAndSortJson(JsonElement jsonTree) {
        writeJsonPaths(jsonTree, "");
        Collections.sort(paths);
    }

    /**
     * @param elem JsonElement
     * @param path current json path
     */
    private void writeJsonPaths(JsonElement elem, String path) {
        if (elem.isJsonObject()) {
            JsonObject jsonObject = elem.getAsJsonObject();
            jsonObject.keySet().forEach(s -> {
                JsonElement jsonElement = jsonObject.get(s);
                if (jsonElement.isJsonPrimitive() || jsonElement.isJsonNull()
                        || (jsonElement.isJsonObject() && jsonElement.getAsJsonObject().keySet().isEmpty())) {
                    if (path.isEmpty()) paths.add(path + s);
                    else paths.add(path + "." + s);
                } else {
                    if (path.isEmpty()) writeJsonPaths(jsonElement, path + s);
                    else writeJsonPaths(jsonElement, path + "." + s);
                }
            });
        } else if (elem.isJsonArray()) {
            JsonArray jsonArray = elem.getAsJsonArray();
            if (jsonArray.size() == 0) paths.add(path + "[*]");
            else jsonArray.forEach(e -> {
                if (e.isJsonArray() || e.isJsonObject()) writeJsonPaths(e, path + "[*]");
                else paths.add(path + "[*]");
            });
        }
    }

    /**
     * @param list full list
     * @return list without duplicates
     */
    private List<String> removeDuplicates(List<String> list) {
        return list.stream().distinct().collect(Collectors.toList());
    }
}
