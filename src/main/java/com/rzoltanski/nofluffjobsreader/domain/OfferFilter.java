package com.rzoltanski.nofluffjobsreader.domain;

import com.rzoltanski.nofluffjobsreader.domain.enumeration.Category;
import com.rzoltanski.nofluffjobsreader.domain.enumeration.Currency;
import com.rzoltanski.nofluffjobsreader.domain.enumeration.Employment;
import com.rzoltanski.nofluffjobsreader.domain.enumeration.OfferStatus;
import com.rzoltanski.nofluffjobsreader.domain.enumeration.SearchType;
import com.rzoltanski.nofluffjobsreader.domain.enumeration.Seniority;
import com.rzoltanski.nofluffjobsreader.domain.enumeration.Technology;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class OfferFilter {

    private MinMaxValue postedOrRenewed;

    private Set<Category> categories;

    private Set<Technology> technologies;

    private ElementsSearch<Seniority> seniorities;

    private ElementsSearch<Employment> employments;

    private Currency currency;

    private OfferStatus offerStatus;

    public record MinMaxValue(Integer min, Integer max) {}

    @Builder
    public record ElementsSearch<T> (Set<T> elements, SearchType searchType) {}
}
