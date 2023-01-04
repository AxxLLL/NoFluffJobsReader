package com.rzoltanski.nofluffjobsreader.domain.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public enum Technology {

    JAVA("Java"),
    JAVA_SPRING("Java-Spring"),
    SPRING("Spring"),
    SQL("SQL"),
    WEB_METHODS("webMethods"),
    DOT_NET(".NET"),
    PYTHON("Python");

    private final String name;

    public static Optional<Technology> findByName(String name) {
        return Arrays.stream(Technology.values())
                .filter(val -> val.getName().equals(name))
                .findFirst();
    }

    public static Technology getByName(String name) {
        return findByName(name)
                .orElseThrow(() -> new IllegalStateException(String.format("Cannot find technology with name '%s'.", name)));
    }
}
