package com.rzoltanski.nofluffjobsreader.utils.sorting;

import com.rzoltanski.nofluffjobsreader.domain.OfferDetails;
import com.rzoltanski.nofluffjobsreader.domain.enumeration.Employment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Component("b2bSalaryMinMaxOfferSorter")
public class B2bSalaryMinMaxOfferSorter implements OfferSorter {

    private static final Comparator<OfferDetails> minSalaryComparator =
            Comparator.comparing(o -> {
                OfferDetails.Salary.Type salary = o.getSalary().getSalaryByEmploymentType(Employment.B2B).orElseThrow();
                return salary.getMin();
            });
    private static final Comparator<OfferDetails> maxSalaryComparator =
            Comparator.comparing(o -> {
                OfferDetails.Salary.Type salary = o.getSalary().getSalaryByEmploymentType(Employment.B2B).orElseThrow();
                return Objects.requireNonNullElse(salary.getMax(), 0);
            });

    @Override
    public List<OfferDetails> sort(List<OfferDetails> offers) {
        List<OfferDetails> mutableList = new ArrayList<>(offers);
        mutableList.sort(minSalaryComparator.thenComparing(maxSalaryComparator));
        return List.copyOf(mutableList);
    }
}
