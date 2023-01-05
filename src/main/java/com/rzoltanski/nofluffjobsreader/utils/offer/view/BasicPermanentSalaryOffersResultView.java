package com.rzoltanski.nofluffjobsreader.utils.offer.view;

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
                .filter(offer -> offer.getSalary().getSalaryByEmploymentType(Employment.PERMANENT).isPresent())
                .map(offer -> {
                    OfferDetails.Salary.Type permanentSalary = offer.getSalary()
                            .getSalaryByEmploymentType(Employment.PERMANENT)
                            .orElseThrow();
                    return List.of(
                            permanentSalary.getMin(),
                            permanentSalary.getMax(),
                            offer.getSalary().getCurrency(),
                            permanentSalary.getPeriod(),
                            StringUtils.join(offer.getSeniority(), ","),
                            offer.getTechnology().getName(),
                            offer.getTitle().replace(";", "|"),
                            offer.getCompanyName().replace(";", "|"),
                            String.join(", ",offer.getMustHaveRequirements()),
                            String.join(", ",offer.getNiceToHaveRequirements()),
                            noFluffJobsClientProperties.getJobDetailsUrl() + offer.getPostingUrl(),
                            offer.getPosted(),
                            offer.getId(),
                            offer.getStatus()
                    );
                })
                .map(values -> values.stream().map(Object::toString).collect(Collectors.toList()))
                .map(values -> String.join(";", values))
                .toList();
    }
}
