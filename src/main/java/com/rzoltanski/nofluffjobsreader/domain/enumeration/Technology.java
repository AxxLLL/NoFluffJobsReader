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
    PHP("PHP"),
    KOTLIN("Kotlin"),
    ORACLE("Oracle"),
    ORACLE_ESSBASE("Oracle Essbase"),
    WEB_METHODS("webMethods"),
    DOT_NET(".NET"),
    SCALA("Scala"),
    SCADA("SCADA"),
    MENDIX("Mendix"),
    GOLANG("Golang"),
    TYPE_SCRIPT("TypeScript"),
    AWS("AWS"),
    AWS_AZURE("AWS / Azure"),
    HASKELL("Haskell"),
    MONGO_DB("MongoDB"),
    SITECORE("Sitecore"),
    MICROSOFT_360_DYNAMICS("Microsoft 365 Dynamics"),
    UNITY_3D("Unity 3D"),
    PL_SQL("PL/SQL"),
    PLSQL("PLSQL"),
    RUBY_ON_RAILS("Ruby on Rails"),
    JAVA_SCRIPT("JavaScript"),
    AL("AL"),
    C("C"),
    C_PLUS_PLUS("C++"),
    C_SHARP("C#"),
    NODE_JS("Node.js"),
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
