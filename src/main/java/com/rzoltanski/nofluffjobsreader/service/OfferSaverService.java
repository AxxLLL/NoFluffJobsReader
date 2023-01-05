package com.rzoltanski.nofluffjobsreader.service;

import java.util.List;

public interface OfferSaverService<T> {

    void save(List<T> offers);
}
