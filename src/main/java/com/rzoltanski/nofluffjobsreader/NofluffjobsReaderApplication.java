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
import com.rzoltanski.nofluffjobsreader.utils.sorting.OfferSorter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
	ApplicationRunner applicationRunner(OfferService service, OfferSorter permanentSalaryMinMaxOfferSorter) {
		return args -> {

			OfferFilter.ElementsSearch<Seniority> seniorities = OfferFilter.ElementsSearch.<Seniority> builder()
					.elements(Set.of(Seniority.JUNIOR))
					.searchType(SearchType.CONTAINS_EXACTLY)
					.build();

			OfferFilter.ElementsSearch<Employment> employments = OfferFilter.ElementsSearch.<Employment> builder()
					.elements(Set.of(Employment.PERMANENT))
					.searchType(SearchType.CONTAINS)
					.build();

			OfferFilter criteria = OfferFilter.builder()
					.seniorities(seniorities)
					.technologies(Set.of(Technology.JAVA, Technology.JAVA_SPRING, Technology.SPRING))
					.employments(employments)
					.categories(Set.of(Category.BACKEND))
					.currency(Currency.PLN)
					.offerStatus(OfferStatus.PUBLISHED)
					.build();

			List<OfferDetails> filtered = permanentSalaryMinMaxOfferSorter.sort(service.getOffers(criteria));

			List<String> csvResults = filtered.stream()
					.map(offer -> {
						OfferDetails.Salary.Type permanentSalary = offer.getSalary().getSalaryByEmploymentType(Employment.PERMANENT);
						Currency currency = offer.getSalary().getCurrency();
						String offerTitle = offer.getTitle();
						String company = offer.getCompanyName();
						String senioritiesStr = StringUtils.join(offer.getSeniority(), ",");
						String technology = offer.getTechnology().getName();
						String category = offer.getCategory().getName();
						Integer renewedDaysAgo = offer.getPostedOrRenewedDaysAgo();
						return permanentSalary.getMin() + ";" + permanentSalary.getMax() + ";" + currency + ";" + technology + ";" + category + ";" + senioritiesStr + ";" + offerTitle + ";" + company + ";" + renewedDaysAgo;
					})
					.toList();

			DateTimeFormatter customFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmss");
			String formattedString = LocalDateTime.now().format(customFormat);

			Path tempFile = Files.createTempFile("offers_" + formattedString, ".csv");
			Files.write(tempFile, csvResults);
			log.info("Saved at path: '{}'", tempFile);

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
