package com.rzoltanski.nofluffjobsreader.service.impl;

import com.google.common.base.Preconditions;
import com.rzoltanski.nofluffjobsreader.client.NoFluffJobsClient;
import com.rzoltanski.nofluffjobsreader.domain.Criteria;
import com.rzoltanski.nofluffjobsreader.domain.OfferDetails;
import com.rzoltanski.nofluffjobsreader.domain.OfferList;
import com.rzoltanski.nofluffjobsreader.service.NoFluffJobsService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("defaultNoFluffJobsService")
@Slf4j
@RequiredArgsConstructor
public class DefaultNoFluffJobsServiceImpl implements NoFluffJobsService {

    private final NoFluffJobsClient client;

    @Override
    public List<OfferList.Offer> getOffers(Criteria criteria) {

        Criteria fixedCriteria = criteria;
        if (criteria == null) {
            fixedCriteria = Criteria.builder().build();
        }

        ResponseEntity<OfferList> response = client.getOffers(fixedCriteria.toCriteriaBody());

        if (!response.getStatusCode().is2xxSuccessful()) {
            log.error("Cannot fetch offers from service. (Response code: {})", response.getStatusCode());
            throw new IllegalStateException("Cannot fetch offers from service.");
        }

        return Optional
                .ofNullable(response.getBody())
                .map(OfferList::getOffers)
                .orElse(List.of());
    }

    @Override
    public OfferDetails getOfferDetails(String offerId) {

        Preconditions.checkArgument(StringUtils.isNotBlank(offerId), "OfferId cannot be blank!");

        ResponseEntity<OfferDetails> response = client.getOfferDetails(offerId);

        if (!response.getStatusCode().is2xxSuccessful()) {
            log.error("Cannot fetch offer details from service. (Response code: {})", response.getStatusCode());
            throw new IllegalStateException("Cannot fetch offer details from service.");
        }

        return response.getBody();
    }
}
