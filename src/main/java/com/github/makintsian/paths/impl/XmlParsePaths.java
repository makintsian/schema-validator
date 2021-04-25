package com.github.makintsian.paths.impl;

import com.github.makintsian.paths.ParsePaths;
import com.github.makintsian.exceptions.SchemaValidatorException;
import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class XmlParsePaths implements ParsePaths {

    private final List<String> paths;

    public XmlParsePaths(InputStream inputStream) {
        this.paths = new ArrayList<>();
        parseXml(inputStream);
    }

    public XmlParsePaths(String xml) {
        this.paths = new ArrayList<>();
        parseXml(IOUtils.toInputStream(xml, StandardCharsets.UTF_8));
    }

    /**
     * @return List with xml paths
     */
    public List<String> getPathsList() {
        return removeDuplicates(paths);
    }

    /**
     * @param inputStream InputStream
     */
    private void parseXml(InputStream inputStream) {
        SAXReader xmlReader = new SAXReader();
        Document document;
        try {
            document = xmlReader.read(inputStream);
        } catch (DocumentException ex) {
            throw new SchemaValidatorException("Xml is not valid", ex);
        }
        writeAndSortXml(document.getRootElement());
    }

    /**
     * @param element Element
     */
    private void writeAndSortXml(Element element) {
        writeXmlPaths(element, element.getName());
        Collections.sort(paths);
    }

    /**
     * @param element Element
     * @param path    current xml path
     */
    private void writeXmlPaths(Element element, String path) {
        element.elements().forEach(elem -> {
            if (elem.elements().isEmpty()) {
                paths.add(path + "/" + elem.getName());
            } else {
                writeXmlPaths(elem, path + "/" + elem.getName());
            }
        });
    }

    /**
     * @param list full list
     * @return list without duplicates
     */
    private List<String> removeDuplicates(List<String> list) {
        return list.stream().distinct().collect(Collectors.toList());
    }
}
