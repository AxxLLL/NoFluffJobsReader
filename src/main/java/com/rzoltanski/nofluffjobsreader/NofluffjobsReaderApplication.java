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
import com.rzoltanski.nofluffjobsreader.service.OfferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties
public class NofluffjobsReaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(NofluffjobsReaderApplication.class, args);
	}

	@Bean
	ApplicationRunner applicationRunner(OfferService service) {
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
					.requirements(Set.of(Technology.JAVA))
					.employments(employments)
					.categories(Set.of(Category.BACKEND))
					.currency(Currency.PLN)
					.offerStatus(OfferStatus.PUBLISHED)
					.build();

			List<OfferDetails> filtered = service.getOffers(criteria)
					.stream()
					.sorted(Comparator.comparing(o -> o.getSalary().getSalaryByEmploymentType(Employment.PERMANENT).getMin()))
					.toList();

			List<String> resultsAsString = filtered.stream()
					.map(offer -> {
						OfferDetails.Salary.Type permSalary = offer.getSalary()
								.getTypes()
								.stream()
								.filter(type -> type.getEmployment() == Employment.PERMANENT)
								.findFirst()
								.orElseThrow();
						return permSalary.getMin() + " - " + permSalary.getMax() + " " + offer.getSalary().getCurrency() + " | " + offer.getTitle();
					})
					.toList();

			resultsAsString.forEach(System.out::println);
		};
	}
}
