package com.rzoltanski.nofluffjobsreader;

import com.rzoltanski.nofluffjobsreader.domain.OfferDetails;
import com.rzoltanski.nofluffjobsreader.domain.OfferFilter;
import com.rzoltanski.nofluffjobsreader.domain.enumeration.Category;
import com.rzoltanski.nofluffjobsreader.domain.enumeration.Currency;
import com.rzoltanski.nofluffjobsreader.domain.enumeration.Employment;
import com.rzoltanski.nofluffjobsreader.domain.enumeration.OfferStatus;
import com.rzoltanski.nofluffjobsreader.domain.enumeration.SearchType;
import com.rzoltanski.nofluffjobsreader.domain.enumeration.Seniority;
import com.rzoltanski.nofluffjobsreader.domain.enumeration.Technology;
import com.rzoltanski.nofluffjobsreader.service.OfferSaverService;
import com.rzoltanski.nofluffjobsreader.service.OfferService;
import com.rzoltanski.nofluffjobsreader.utils.offer.view.OffersResultView;
import com.rzoltanski.nofluffjobsreader.utils.sorting.OfferSorter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Set;

@SpringBootApplication
@EnableConfigurationProperties
public class NofluffjobsReaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(NofluffjobsReaderApplication.class, args);
	}

	@Bean
	ApplicationRunner applicationRunner(@Qualifier("csvFileOfferSaverService") OfferSaverService<String> csvOfferSaverService,
										@Qualifier("consoleOfferSaverService") OfferSaverService<String> consoleOfferSaverService,
										@Qualifier("basicPermanentSalaryOffersResultView") OffersResultView<String> basicPermanentSalaryOffersResultView,
										@Qualifier("permanentSalaryMinMaxOfferSorter") OfferSorter offerSorter,
										OfferService offerService
	) {
		return args -> {

			OfferFilter.ElementsSearch<Seniority> seniorities = OfferFilter.ElementsSearch.<Seniority> builder()
					.elements(Set.of(Seniority.MID))
					.searchType(SearchType.CONTAINS_EXACTLY)
					.build();

			OfferFilter.ElementsSearch<Employment> employments = OfferFilter.ElementsSearch.<Employment> builder()
					.elements(Set.of(Employment.PERMANENT))
					.searchType(SearchType.CONTAINS)
					.build();

			OfferFilter criteria = OfferFilter.builder()
					.seniorities(seniorities)
					.technologies(Set.of(Technology.JAVA, Technology.SPRING, Technology.JAVA_SPRING))
					.employments(employments)
					.categories(Set.of(Category.BACKEND))
					.currency(Currency.PLN)
					.offerStatus(OfferStatus.PUBLISHED)
					.build();

			List<OfferDetails> filtered = offerSorter.sort(offerService.getOffers(criteria));
			List<String> results = basicPermanentSalaryOffersResultView.getResults(filtered);
			csvOfferSaverService.save(results);
			consoleOfferSaverService.save(results);
		};
	}
}
