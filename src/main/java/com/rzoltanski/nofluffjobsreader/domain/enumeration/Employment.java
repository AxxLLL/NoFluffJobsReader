package com.rzoltanski.nofluffjobsreader.domain.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum Employment {

    PERMANENT("permanent"),
    B2B("b2b"),
    MANDATE_CONTRACT("zlecenie");

    private final String name;

    public static Employment getByName(String name) {
        return Arrays.stream(Employment.values())
                .filter(val -> val.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("Cannot find employment type with name '%s'.", name)));
    }
}
