package com.rzoltanski.nofluffjobsreader.service;

import com.rzoltanski.nofluffjobsreader.domain.Criteria;
import com.rzoltanski.nofluffjobsreader.domain.OfferDetails;
import com.rzoltanski.nofluffjobsreader.domain.OfferList;

import java.util.List;

public interface NoFluffJobsService {

    List<OfferList.Offer> getOffers(Criteria criteria);

    OfferDetails getOfferDetails(String offerId);
}
