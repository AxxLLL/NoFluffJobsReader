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
    ANGULAR("Angular"),
    REACT("React"),
    REACTNATIVE("ReactNative"),
    REACT_NATIVE("React native"),
    ANSIBLE("Ansible"),
    ANDROID("Android"),
    WEBCON("WEBCON"),
    K8S("K8S"),
    IOS("iOS"),
    XAMARIN("Xamarin"),
    APEX("Apex"),
    JENKINS("Jenkins"),
    AZURE("Azure"),
    PERL("perl"),
    CLOUD("Cloud"),
    HTML("HTML"),
    ELIXIR("Elixir"),
    DELPHI("Delphi"),
    HTML_CSS("html&css"),
    VUE("Vue.js"),
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
    LINUX("Linux"),
    UNITY("UNITY"),
    FLUTTER("Flutter"),
    SPARK("Spark"),
    DOCKER("Docker"),
    FLEXCUBE("Flexcube"),
    SALESFORCE("Salesforce"),
    CYPRESS("Cypress"),
    HADOOP("Hadoop"),
    TERRAFORM("Terraform"),
    DRUPAL("Drupal"),
    LWC("LWC"),
    OUTSYSTEMS("Outsystems"),
    DJANGO("Django"),
    JIRA("JIRA"),
    SNOW_FLAKE("Snowflake"),
    FPGA("FPGA"),
    KUBERNETES("Kubernetes"),
    MAGENTO("Magento"),
    RUST("Rust"),
    GCP("GCP"),
    CI_CD("CI/CD"),
    RUBY("Ruby"),
    SELENIUM("Selenium"),
    OPEN_SHIFT("OpenShift"),
    MICROSOFT_DYNAMICS_365("Microsoft Dynamics 365"),
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
