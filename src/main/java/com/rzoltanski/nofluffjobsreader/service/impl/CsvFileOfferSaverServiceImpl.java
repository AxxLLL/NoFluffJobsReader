package com.rzoltanski.nofluffjobsreader.service.impl;

import com.rzoltanski.nofluffjobsreader.service.OfferSaverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service("csvFileOfferSaverService")
public class CsvFileOfferSaverServiceImpl implements OfferSaverService<String> {

    @Override
    public void save(List<String> offers) {

        DateTimeFormatter customFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmss");
        String formattedString = LocalDateTime.now().format(customFormat);

        try {
            Path tempFile = Files.createTempFile("offers_" + formattedString + "_", ".csv");
            Files.write(tempFile, offers);
            log.info("Saved at path: '{}'", tempFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
