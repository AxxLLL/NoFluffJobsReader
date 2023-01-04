package com.rzoltanski.nofluffjobsreader.domain;

import com.rzoltanski.nofluffjobsreader.domain.enumeration.Category;
import com.rzoltanski.nofluffjobsreader.domain.enumeration.Employment;
import com.rzoltanski.nofluffjobsreader.domain.enumeration.Seniority;
import com.rzoltanski.nofluffjobsreader.domain.enumeration.Technology;
import lombok.Builder;

import java.util.Set;
import java.util.stream.Collectors;

@Builder
public record Criteria (Set<Category> categories, Set<Technology> technologies, Set<Seniority> seniorities, Set<Employment> employments) {

    public String toCriteriaBody() {
        StringBuilder builder = new StringBuilder("{\"rawSearch\":\"");

        /*
         * Wyszukuje po alternatywie.
         * */
        if (categories != null && !categories.isEmpty()) {
            String categoriesFilter = categories.stream().map(Category::getName).collect(Collectors.joining(","));
            builder.append(" category=").append(categoriesFilter);
        }

        /*
         * Wyszukuje po części wspólnej.
         * */
        if (technologies != null && !technologies.isEmpty()) {
            String requirementsFilter = technologies.stream().map(Technology::getName).collect(Collectors.joining(","));
            builder.append(" requirement=").append(requirementsFilter);
        }

        /*
         * Wyszukuje po alternatywie.
         * */
        if (seniorities != null && !seniorities.isEmpty()) {
            String senioritiesFilter = seniorities.stream().map(Seniority::getName).collect(Collectors.joining(","));
            builder.append(" seniority=").append(senioritiesFilter);
        }

        /*
         * Wyszukuje po alternatywie.
         * */
        if (employments != null && !employments.isEmpty()) {
            String employmentsFilter = employments.stream().map(Employment::getName).collect(Collectors.joining(","));
            builder.append(" employment=").append(employmentsFilter);
        }

        builder.append("\"}");
        return builder.toString();
    }
}