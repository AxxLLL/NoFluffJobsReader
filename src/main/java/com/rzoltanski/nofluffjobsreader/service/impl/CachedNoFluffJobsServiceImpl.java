package com.rzoltanski.nofluffjobsreader.service.impl;

import com.rzoltanski.nofluffjobsreader.domain.Criteria;
import com.rzoltanski.nofluffjobsreader.domain.OfferDetails;
import com.rzoltanski.nofluffjobsreader.domain.OfferList;
import com.rzoltanski.nofluffjobsreader.service.NoFluffJobsService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.List;

@Service("cachedNoFluffJobsService")
public class CachedNoFluffJobsServiceImpl implements NoFluffJobsService {

    private final NoFluffJobsService noFluffJobsService;

    public CachedNoFluffJobsServiceImpl(
            @Qualifier("defaultNoFluffJobsService") NoFluffJobsService noFluffJobsService) {
        this.noFluffJobsService = noFluffJobsService;
    }

    @Override
    @Cacheable(value = "offersList", keyGenerator = "criteriaCacheKeyGenerator")
    public List<OfferList.Offer> getOffers(Criteria criteria) {
        return noFluffJobsService.getOffers(criteria);
    }

    @Override
    @Cacheable("offersDetails")
    public OfferDetails getOfferDetails(String offerId) {
        return noFluffJobsService.getOfferDetails(offerId);
    }

    @Component("criteriaCacheKeyGenerator")
    public static class CriteriaCacheKeyGenerator implements KeyGenerator {

        private static final String NO_ARGS_KEY = "no-args";

        @Override
        public Object generate(Object target, Method method, Object... params) {

            if (params.length == 0 || params[0] == null) {
                return NO_ARGS_KEY;
            }

            Criteria criteria = (Criteria) params[0];
            return criteria.toCriteriaBody();
        }
    }
}
