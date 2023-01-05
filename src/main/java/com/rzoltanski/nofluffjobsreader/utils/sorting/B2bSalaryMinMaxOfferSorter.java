package com.rzoltanski.nofluffjobsreader.utils.sorting;

import com.rzoltanski.nofluffjobsreader.domain.OfferDetails;
import com.rzoltanski.nofluffjobsreader.domain.enumeration.Employment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component("b2bSalaryMinMaxOfferSorter")
public class B2bSalaryMinMaxOfferSorter implements OfferSorter {

    private static final Comparator<OfferDetails> minSalaryComparator =
            Comparator.comparing(o -> o.getSalary().getSalaryByEmploymentType(Employment.B2B).getMin());
    private static final Comparator<OfferDetails> maxSalaryComparator =
            Comparator.comparing(o -> o.getSalary().getSalaryByEmploymentType(Employment.B2B).getMax() == null ? 0 : o.getSalary().getSalaryByEmploymentType(Employment.B2B).getMax());

    @Override
    public List<OfferDetails> sort(List<OfferDetails> offers) {
        List<OfferDetails> mutableList = new ArrayList<>(offers);
        mutableList.sort(minSalaryComparator.thenComparing(maxSalaryComparator));
        return List.copyOf(mutableList);
    }
}
