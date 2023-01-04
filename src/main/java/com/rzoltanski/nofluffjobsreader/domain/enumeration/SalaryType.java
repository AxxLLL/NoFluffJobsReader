package com.rzoltanski.nofluffjobsreader.domain.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum SalaryType {

    PERMANENT("permanent"),
    B2B("b2b"),
    MANDATE_CONTRACT("zlecenie");

    private final String name;

    public static SalaryType getByName(String name) {
        return Arrays.stream(SalaryType.values())
                .filter(val -> val.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("Cannot find salary type with name '%s'.", name)));
    }
}
