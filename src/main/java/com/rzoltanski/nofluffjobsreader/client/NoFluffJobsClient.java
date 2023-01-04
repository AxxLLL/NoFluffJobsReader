package com.rzoltanski.nofluffjobsreader.client;

import com.rzoltanski.nofluffjobsreader.domain.OfferDetails;
import com.rzoltanski.nofluffjobsreader.domain.OfferList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange(url = "/api", contentType = "application/json", accept = "application/json")
public interface NoFluffJobsClient {

    @PostExchange("/search/posting")
    ResponseEntity<OfferList> getOffers(@RequestBody String body);

    @GetExchange("/posting/{offerId}")
    ResponseEntity<OfferDetails> getOfferDetails(@PathVariable("offerId") String offerId);
}