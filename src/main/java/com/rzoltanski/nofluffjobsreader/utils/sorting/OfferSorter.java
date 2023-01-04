package com.rzoltanski.nofluffjobsreader.utils.sorting;

import com.rzoltanski.nofluffjobsreader.domain.OfferDetails;

import java.util.List;

public interface OfferSorter {

    List<OfferDetails> sort(List<OfferDetails> offers);
}
