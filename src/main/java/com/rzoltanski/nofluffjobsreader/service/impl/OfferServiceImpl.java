package com.rzoltanski.nofluffjobsreader.service.impl;

import com.rzoltanski.nofluffjobsreader.domain.Criteria;
import com.rzoltanski.nofluffjobsreader.domain.OfferDetails;
import com.rzoltanski.nofluffjobsreader.domain.OfferFilter;
import com.rzoltanski.nofluffjobsreader.domain.enumeration.Employment;
import com.rzoltanski.nofluffjobsreader.domain.enumeration.Seniority;
import com.rzoltanski.nofluffjobsreader.service.NoFluffJobsService;
import com.rzoltanski.nofluffjobsreader.service.NoFluffJobsServiceFactory;
import com.rzoltanski.nofluffjobsreader.service.OfferService;
import com.rzoltanski.nofluffjobsreader.utils.offer.OfferDetailsFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        offersStream = OfferDetailsFilter.OFFER_STATUS_FILTER.apply(offersStream, filter);
        offersStream = OfferDetailsFilter.CURRENCY_FILTER.apply(offersStream, filter);
        offersStream = OfferDetailsFilter.TECHNOLOGIES_FILTER.apply(offersStream, filter);
        offersStream = OfferDetailsFilter.POSTED_OR_RENEWED_FILTER.apply(offersStream, filter);
        offersStream = OfferDetailsFilter.EMPLOYMENTS_FILTER.apply(offersStream, filter);
        offersStream = OfferDetailsFilter.SENIORITIES_FILTER.apply(offersStream, filter);
        return offersStream.toList();
    }
}
