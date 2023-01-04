package com.rzoltanski.nofluffjobsreader.service;

import com.rzoltanski.nofluffjobsreader.domain.OfferDetails;
import com.rzoltanski.nofluffjobsreader.domain.OfferFilter;

import java.util.List;

public interface OfferService {

    List<OfferDetails> getOffers(OfferFilter filter);
}
