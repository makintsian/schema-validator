# schema-validator

[![Build Status](https://travis-ci.org/makintsian/schema-validator.svg?branch=master)](https://travis-ci.org/makintsian/schema-validator)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.makintsian/schema-validator/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.makintsian/schema-validator/)
[![GitHub release](https://img.shields.io/github/release/makintsian/schema-validator.svg)](https://github.com/makintsian/schema-validator/releases)

_Validates json and xml paths_

## Download

## Example

### Files .json and .xml

```
{
  "firstName": "John",
  "lastName": "Doe",
  "age": 26,
  "address": {
    "streetAddress": "naist street",
    "city": "Nara",
    "postalCode": "630-0192"
  }
  "phoneNumbers": [
    {
      "type": "iPhone",
      "number": "0123-4567-8888"
    },
    {
      "type": "home",
      "number": "0123-4567-8910"
    }
  ]
}
```

```
<breakfast_menu>
    <food>
        <name>Belgian Waffles</name>
        <price>$5.95</price>
        <description>Two of our famous Belgian Waffles</description>
        <calories>650</calories>
    </food>
</breakfast_menu>
```

### Schema .yaml

```
json_schema:
  - 'firstName'
  - 'lastName'
  - 'age'
  - 'address.streetAddress'
  - 'address.city'
  - 'address.postalCode'
  - 'phoneNumbers[*].type'
  - 'phoneNumbers[*].number'
xml_schema:
  - 'breakfast_menu/food/name'
  - 'breakfast_menu/food/price'
  - 'breakfast_menu/food/description'
  - 'breakfast_menu/food/calories'
```

### Parse json and xml

```
ParsePaths parsePaths = new JsonParsePaths(${json});
List<String> paths = parsePaths.getPathsList();
```

```
ParsePaths parsePaths = new XmlParsePaths(${xml});
List<String> paths = parsePaths.getPathsList();
```

### Validate json and xml

```
SchemaValidator schemaValidator = SchemaValidator.newBuilder()
                .withSchema(${schema})
                .build();
schemaValidator.validateJson(${json}, "json_schema");
```

```
SchemaValidator schemaValidator = SchemaValidator.newBuilder()
                .withSchema(${schema})
                .build();
schemaValidator.validateXml(${xml}, "xml_schema");
```