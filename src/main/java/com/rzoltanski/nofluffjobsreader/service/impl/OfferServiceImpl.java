package com.rzoltanski.nofluffjobsreader.service.impl;

import com.rzoltanski.nofluffjobsreader.domain.Criteria;
import com.rzoltanski.nofluffjobsreader.domain.OfferDetails;
import com.rzoltanski.nofluffjobsreader.domain.OfferFilter;
import com.rzoltanski.nofluffjobsreader.domain.enumeration.Employment;
import com.rzoltanski.nofluffjobsreader.domain.enumeration.SearchType;
import com.rzoltanski.nofluffjobsreader.domain.enumeration.Seniority;
import com.rzoltanski.nofluffjobsreader.service.NoFluffJobsService;
import com.rzoltanski.nofluffjobsreader.service.NoFluffJobsServiceFactory;
import com.rzoltanski.nofluffjobsreader.service.OfferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class OfferServiceImpl implements OfferService {

    private final NoFluffJobsService noFluffJobsService;

    public OfferServiceImpl(NoFluffJobsServiceFactory noFluffJobsServiceFactory) {
        noFluffJobsService = noFluffJobsServiceFactory.getService();
    }

    @Override
    public List<OfferDetails> getOffers(OfferFilter filter) {

        Set<Employment> employments = Optional.ofNullable(filter.getEmployments()).map(OfferFilter.ElementsSearch::elements).orElse(null);
        Set<Seniority> seniorities = Optional.ofNullable(filter.getSeniorities()).map(OfferFilter.ElementsSearch::elements).orElse(null);

        Criteria criteria = Criteria.builder()
                .categories(filter.getCategories())
                .seniorities(seniorities)
                .employments(employments)
                .build();

        List<OfferDetails> offers = noFluffJobsService.getOffers(criteria)
                .stream()
                .map(offer -> {
                    try {
                        return noFluffJobsService.getOfferDetails(offer.getId());
                    } catch (Exception e) {
                        log.error("Exception: {}", e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return applyFilters(offers, filter);
    }

    private List<OfferDetails> applyFilters(List<OfferDetails> offers, OfferFilter filter) {

        Stream<OfferDetails> offersStream = offers.stream();

        if (filter.getOfferStatus() != null) {
            offersStream = offersStream.filter(offer -> offer.getStatus() == filter.getOfferStatus());
        }

        if (filter.getCurrency() != null) {
            offersStream = offersStream.filter(offer -> offer.getSalary().getCurrency() == filter.getCurrency());
        }

        if (filter.getTechnologies() != null) {
            offersStream = offersStream.filter(offer -> offer.getTechnology() != null && filter.getTechnologies().contains(offer.getTechnology()));
        }

        OfferFilter.MinMaxValue postedOrRenewed = filter.getPostedOrRenewed();
        if (postedOrRenewed != null) {
            int fixedMin = Objects.requireNonNullElse(postedOrRenewed.min(), 0);
            int fixedMax = Objects.requireNonNullElse(postedOrRenewed.min(), Integer.MAX_VALUE);
            offersStream = offersStream.filter(offer -> offer.getPostedOrRenewedDaysAgo() >= fixedMin && offer.getPostedOrRenewedDaysAgo() <= fixedMax);
        }

        OfferFilter.ElementsSearch<Employment> employments = filter.getEmployments();
        if (employments != null) {
            Set<Employment> elements = employments.elements();
            if (employments.searchType() == SearchType.ALTERNATIVE) {
                offersStream = offersStream
                        .filter(offer -> offer.getSalary().getTypes().stream().anyMatch(salary -> elements.contains(salary.getEmployment())));
            } else if (employments.searchType() == SearchType.CONTAINS){
                offersStream = offersStream
                        .filter(offer -> offer.getSalary()
                                .getTypes()
                                .stream()
                                .map(OfferDetails.Salary.Type::getEmployment)
                                .collect(Collectors.toSet())
                                .containsAll(elements));
            } else if (employments.searchType() == SearchType.CONTAINS_EXACTLY) {
                offersStream = offersStream
                        .filter(offer -> {
                            Set<Employment> offerEmployments = offer.getSalary()
                                    .getTypes()
                                    .stream()
                                    .map(OfferDetails.Salary.Type::getEmployment)
                                    .collect(Collectors.toSet());
                            return offerEmployments.containsAll(elements) && elements.size() == offerEmployments.size();

                        });
            }
        }

        OfferFilter.ElementsSearch<Seniority> seniorities = filter.getSeniorities();
        if (seniorities != null) {
            Set<Seniority> elements = seniorities.elements();
            if (seniorities.searchType() == SearchType.ALTERNATIVE) {
                offersStream = offersStream
                        .filter(offer -> offer.getSeniority().stream().anyMatch(elements::contains));
            } else if (seniorities.searchType() == SearchType.CONTAINS){
                offersStream = offersStream
                        .filter(offer -> new HashSet<>(offer.getSeniority()).containsAll(elements));
            } else if (seniorities.searchType() == SearchType.CONTAINS_EXACTLY) {
                offersStream = offersStream
                        .filter(offer -> {
                            Set<Seniority> offerSeniorities = new HashSet<>(offer.getSeniority());
                            return offerSeniorities.containsAll(elements) && elements.size() == offerSeniorities.size();

                        });
            }
        }

        return offersStream.toList();
    }


}
