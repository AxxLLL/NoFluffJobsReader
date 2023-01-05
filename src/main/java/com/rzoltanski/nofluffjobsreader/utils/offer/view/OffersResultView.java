package com.rzoltanski.nofluffjobsreader.utils.offer.view;

import com.rzoltanski.nofluffjobsreader.domain.OfferDetails;

import java.util.List;

public interface OffersResultView<T> {

    List<T> getResults(List<OfferDetails> offers);
}
