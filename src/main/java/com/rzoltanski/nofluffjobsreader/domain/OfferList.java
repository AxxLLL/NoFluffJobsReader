package com.rzoltanski.nofluffjobsreader.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rzoltanski.nofluffjobsreader.domain.enumeration.Category;
import com.rzoltanski.nofluffjobsreader.domain.enumeration.Technology;
import lombok.Data;

import java.util.List;

@Data
public class OfferList {

    @JsonProperty("postings")
    private List<Offer> offers;

    @Data
    public static class Offer {
        private String id;
        private String name;
        private String title;
        private Category category;
        private Technology technology;

        @JsonProperty("category")
        private void unpackCategory(String category) {
            this.category = Category.getByName(category);
        }

        @JsonProperty("technology")
        private void unpackTechnology(String technology) {
            this.technology = Technology.findByName(technology).orElse(null);
        }
    }
}
