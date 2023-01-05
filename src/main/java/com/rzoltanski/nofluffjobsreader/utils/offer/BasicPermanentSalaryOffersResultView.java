package com.rzoltanski.nofluffjobsreader.utils.offer;

import com.rzoltanski.nofluffjobsreader.configuration.NoFluffJobsClientProperties;
import com.rzoltanski.nofluffjobsreader.domain.OfferDetails;
import com.rzoltanski.nofluffjobsreader.domain.enumeration.Employment;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("basicPermanentSalaryOffersResultView")
@RequiredArgsConstructor
public class BasicPermanentSalaryOffersResultView implements OffersResultView<String> {

    private final NoFluffJobsClientProperties noFluffJobsClientProperties;

    @Override
    public List<String> getResults(List<OfferDetails> offers) {
        return offers.stream()
                .map(offer -> List.of(
                        offer.getSalary().getSalaryByEmploymentType(Employment.PERMANENT).getMin(),
                        offer.getSalary().getSalaryByEmploymentType(Employment.PERMANENT).getMax(),
                        offer.getSalary().getCurrency(),
                        offer.getSalary().getSalaryByEmploymentType(Employment.PERMANENT).getPeriod(),
                        offer.getTitle().replace(";", "|"),
                        StringUtils.join(offer.getSeniority(), ","),
                        offer.getTechnology().getName(),
                        offer.getPosted(),
                        offer.getCompanyName().replace(";", "|"),
                        offer.getStatus(),
                        offer.getId(),
                        noFluffJobsClientProperties.getJobDetailsUrl() + offer.getPostingUrl()
                ))
                .map(values -> values.stream().map(Object::toString).collect(Collectors.toList()))
                .map(values -> String.join(";", values))
                .toList();
    }
}
