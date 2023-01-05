package com.rzoltanski.nofluffjobsreader.utils.offer;

import com.rzoltanski.nofluffjobsreader.domain.OfferDetails;
import com.rzoltanski.nofluffjobsreader.domain.OfferFilter;
import com.rzoltanski.nofluffjobsreader.domain.enumeration.Employment;
import com.rzoltanski.nofluffjobsreader.domain.enumeration.Seniority;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OfferDetailsFilter {

    public static final BiFunction<Stream<OfferDetails>, OfferFilter, Stream<OfferDetails>> OFFER_STATUS_FILTER = (offerStream, filter) -> {
        if (filter.getOfferStatus() == null) {
            return offerStream;
        }
        return offerStream.filter(offer -> offer.getStatus() == filter.getOfferStatus());
    };

    public static final BiFunction<Stream<OfferDetails>, OfferFilter, Stream<OfferDetails>> CURRENCY_FILTER = (offerStream, filter) -> {
        if (filter.getCurrency() == null) {
            return offerStream;
        }
        return offerStream.filter(offer -> offer.getSalary().getCurrency() == filter.getCurrency());
    };

    public static final BiFunction<Stream<OfferDetails>, OfferFilter, Stream<OfferDetails>> TECHNOLOGIES_FILTER = (offerStream, filter) -> {
        if (filter.getTechnologies() == null) {
            return offerStream;
        }
        return offerStream.filter(offer -> offer.getTechnology() != null && filter.getTechnologies().contains(offer.getTechnology()));
    };

    public static final BiFunction<Stream<OfferDetails>, OfferFilter, Stream<OfferDetails>> POSTED_OR_RENEWED_FILTER = (offerStream, filter) -> {

        OfferFilter.MinMaxValue postedOrRenewed = filter.getPostedOrRenewed();

        if (postedOrRenewed == null) {
            return offerStream;
        }

        int fixedMin = Objects.requireNonNullElse(postedOrRenewed.min(), 0);
        int fixedMax = Objects.requireNonNullElse(postedOrRenewed.min(), Integer.MAX_VALUE);
        return offerStream.filter(offer -> offer.getPostedOrRenewedDaysAgo() >= fixedMin && offer.getPostedOrRenewedDaysAgo() <= fixedMax);
    };

    public static final BiFunction<Stream<OfferDetails>, OfferFilter, Stream<OfferDetails>> EMPLOYMENTS_FILTER = (offerStream, filter) -> {

        OfferFilter.ElementsSearch<Employment> employments = filter.getEmployments();

        if (employments == null) {
            return offerStream;
        }

        Set<Employment> elements = employments.elements();
        return switch (employments.searchType()) {
            case ALTERNATIVE ->
                    offerStream.filter(offer -> offer.getSalary().getTypes().stream().anyMatch(salary -> elements.contains(salary.getEmployment())));
            case CONTAINS ->
                    offerStream.filter(offer -> offer.getSalary()
                            .getTypes()
                            .stream()
                            .map(OfferDetails.Salary.Type::getEmployment)
                            .collect(Collectors.toSet())
                            .containsAll(elements));
            case CONTAINS_EXACTLY ->
                    offerStream.filter(offer -> {
                        Set<Employment> offerEmployments = offer.getSalary()
                                .getTypes()
                                .stream()
                                .map(OfferDetails.Salary.Type::getEmployment)
                                .collect(Collectors.toSet());
                        return offerEmployments.containsAll(elements) && elements.size() == offerEmployments.size();

                    });
        };
    };

    public static final BiFunction<Stream<OfferDetails>, OfferFilter, Stream<OfferDetails>> SENIORITIES_FILTER = (offerStream, filter) -> {

        OfferFilter.ElementsSearch<Seniority> seniorities = filter.getSeniorities();

        if (seniorities == null) {
            return offerStream;
        }

        Set<Seniority> elements = seniorities.elements();
        return switch (seniorities.searchType()) {
            case ALTERNATIVE -> offerStream.filter(offer -> offer.getSeniority().stream().anyMatch(elements::contains));
            case CONTAINS -> offerStream.filter(offer -> new HashSet<>(offer.getSeniority()).containsAll(elements));
            case CONTAINS_EXACTLY -> offerStream.filter(offer -> {
                        Set<Seniority> offerSeniorities = new HashSet<>(offer.getSeniority());
                        return offerSeniorities.containsAll(elements) && elements.size() == offerSeniorities.size();
                    });
        };
    };
}
