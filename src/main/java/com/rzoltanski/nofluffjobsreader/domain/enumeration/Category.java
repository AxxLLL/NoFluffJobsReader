package com.rzoltanski.nofluffjobsreader.domain.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum Category {

    BACKEND("backend"),
    SALES("sales"),
    OTHER("other"),
    UX("ux"),
    BACKOFFICE("backoffice"),
    HR("hr"),
    AGILE("agile"),
    GAMING("gaming"),
    SUPPORT("support"),
    BIG_DATA("bigData"),
    BUSINESS_INTELLIGENCE("businessIntelligence"),
    BUSINESS_ANALYST("businessAnalyst"),
    MARKETING("marketing"),
    TESTING("testing"),
    DEVOPS("devops"),
    SECURITY("security"),
    FRONTEND("frontend"),
    PROJECT_MANAGER("projectManager"),
    PRODUCT_MANAGEMENT("productManagement"),
    IT_ADMINISTRATOR("itAdministrator"),
    MOBILE("mobile"),
    EMBEDDED("embedded"),
    ARTIFICIAL_INTELLIGENCE("artificialIntelligence"),
    FULLSTACK("fullstack");

    private final String name;

    public static Category getByName(String category) {
        return Arrays.stream(Category.values())
                .filter(val -> val.getName().equals(category))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("Cannot find category with name '%s'.", category)));
    }
}
