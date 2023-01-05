package com.rzoltanski.nofluffjobsreader.domain.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public enum Technology {

    AL("AL"),
    ANDROID("Android"),
    ANGULAR("Angular"),
    ANSIBLE("Ansible"),
    APEX("Apex"),
    AWS("AWS"),
    AWS_AZURE("AWS / Azure"),
    AZURE("Azure"),

    C("C"),
    C_PLUS_PLUS("C++"),
    C_SHARP("C#"),
    CI_CD("CI/CD"),
    CLOUD("Cloud"),
    CYPRESS("Cypress"),

    DELPHI("Delphi"),
    DJANGO("Django"),
    DOCKER("Docker"),
    DOT_NET(".NET"),
    DRUPAL("Drupal"),

    ELIXIR("Elixir"),

    FLEXCUBE("Flexcube"),
    FLUTTER("Flutter"),
    FPGA("FPGA"),

    GCP("GCP"),
    GOLANG("Golang"),

    HADOOP("Hadoop"),
    HASKELL("Haskell"),
    HTML("HTML"),
    HTML_CSS("html&css"),

    IOS("iOS"),

    JAVA("Java"),
    JAVA_SCRIPT("JavaScript"),
    JAVA_SPRING("Java-Spring"),
    JENKINS("Jenkins"),
    JIRA("JIRA"),

    KOTLIN("Kotlin"),
    KUBERNETES("Kubernetes"),
    K8S("K8S"),

    LINUX("Linux"),
    LWC("LWC"),

    MAGENTO("Magento"),
    MENDIX("Mendix"),
    MICROSOFT_DYNAMICS_365("Microsoft Dynamics 365"),
    MICROSOFT_360_DYNAMICS("Microsoft 365 Dynamics"),
    MONGO_DB("MongoDB"),

    NODE_JS("Node.js"),

    OPEN_SHIFT("OpenShift"),
    ORACLE("Oracle"),
    ORACLE_ESSBASE("Oracle Essbase"),
    OUTSYSTEMS("Outsystems"),

    PERL("perl"),
    PHP("PHP"),
    PLSQL("PLSQL"),
    PL_SQL("PL/SQL"),
    PYTHON("Python"),

    REACT("React"),
    REACTNATIVE("ReactNative"),
    REACT_NATIVE("React native"),
    RUBY("Ruby"),
    RUBY_ON_RAILS("Ruby on Rails"),
    RUST("Rust"),

    SALESFORCE("Salesforce"),
    SCADA("SCADA"),
    SCALA("Scala"),
    SELENIUM("Selenium"),
    SITECORE("Sitecore"),
    SNOW_FLAKE("Snowflake"),
    SPARK("Spark"),
    SPRING("Spring"),
    SQL("SQL"),

    TERRAFORM("Terraform"),
    TYPE_SCRIPT("TypeScript"),

    UNITY("UNITY"),
    UNITY_3D("Unity 3D"),

    VUE("Vue.js"),

    WEBCON("WEBCON"),
    WEB_METHODS("webMethods"),

    XAMARIN("Xamarin");

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
