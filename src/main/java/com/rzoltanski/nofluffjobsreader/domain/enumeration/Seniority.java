package com.rzoltanski.nofluffjobsreader.domain.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum Seniority {

    EXPERT("Expert"),
    JUNIOR("Junior"),
    MID("Mid"),
    SENIOR("Senior"),
    TRAINEE("Trainee");

    private final String name;

    public static Seniority getByName(String name) {
        return Arrays.stream(Seniority.values())
                .filter(val -> val.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("Cannot find seniority with name '%s'.", name)));
    }
}
