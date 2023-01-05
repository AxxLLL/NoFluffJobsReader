package com.rzoltanski.nofluffjobsreader.domain.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum Category {

    AGILE("agile"),
    ARTIFICIAL_INTELLIGENCE("artificialIntelligence"),
    BACKEND("backend"),
    BACKOFFICE("backoffice"),
    BIG_DATA("bigData"),
    BUSINESS_ANALYST("businessAnalyst"),
    BUSINESS_INTELLIGENCE("businessIntelligence"),
    DEVOPS("devops"),
    EMBEDDED("embedded"),
    FRONTEND("frontend"),
    FULLSTACK("fullstack"),
    GAMING("gaming"),
    HR("hr"),
    IT_ADMINISTRATOR("itAdministrator"),
    MARKETING("marketing"),
    MOBILE("mobile"),
    OTHER("other"),
    PRODUCT_MANAGEMENT("productManagement"),
    PROJECT_MANAGER("projectManager"),
    SALES("sales"),
    SECURITY("security"),
    SUPPORT("support"),
    TESTING("testing"),
    UX("ux");

    private final String name;

    public static Category getByName(String category) {
        return Arrays.stream(Category.values())
                .filter(val -> val.getName().equals(category))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("Cannot find category with name '%s'.", category)));
    }
}
