package com.rzoltanski.nofluffjobsreader.service.impl;

import com.rzoltanski.nofluffjobsreader.service.OfferSaverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("consoleOfferSaverService")
public class ConsoleOfferSaverServiceImpl implements OfferSaverService<String> {

    @Override
    public void save(List<String> offers) {
        offers.forEach(log::info);
    }
}
