package com.rzoltanski.nofluffjobsreader;

import com.rzoltanski.nofluffjobsreader.domain.Criteria;
import com.rzoltanski.nofluffjobsreader.domain.OfferDetails;
import com.rzoltanski.nofluffjobsreader.domain.enumeration.Category;
import com.rzoltanski.nofluffjobsreader.domain.enumeration.Currency;
import com.rzoltanski.nofluffjobsreader.domain.enumeration.OfferStatus;
import com.rzoltanski.nofluffjobsreader.domain.enumeration.SalaryType;
import com.rzoltanski.nofluffjobsreader.domain.enumeration.Seniority;
import com.rzoltanski.nofluffjobsreader.domain.enumeration.Technology;
import com.rzoltanski.nofluffjobsreader.service.NoFluffJobsService;
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
	ApplicationRunner applicationRunner(NoFluffJobsService service) {
		return args -> {


//			client.getOfferDetails("VXANA6AY");

			Criteria criteria = Criteria.builder()
					.seniorities(Set.of(Seniority.MID))
					.technologies(Set.of(Technology.JAVA))
					.employments(Set.of(SalaryType.PERMANENT))
					.categories(Set.of(Category.BACKEND))
					.build();
			List<OfferDetails> collect = service.getOffers(criteria).stream()
					.map(offer -> service.getOfferDetails(offer.getId()))
					.toList();


			List<OfferDetails> filtered = collect.stream()
					.filter(offer -> offer.getTechnology() == Technology.JAVA)
					.filter(offer -> offer.getSeniority().contains(Seniority.MID) && offer.getSeniority().size() == 1)
					.filter(offer -> offer.getSalary().getCurrency() == Currency.PLN)
					.filter(offer -> offer.getSalary().getTypes().stream().anyMatch(type -> type.getType() == SalaryType.PERMANENT))
					.filter(offer -> offer.getStatus() == OfferStatus.PUBLISHED)
					.filter(offer -> offer.getPostedOrRenewedDaysAgo() <= 31)
					.sorted(Comparator.comparing(o -> o.getSalary().getSalaryType(SalaryType.PERMANENT).getMin()))
					.toList();

			List<String> resultsAsString = filtered.stream()
					.map(offer -> {
						OfferDetails.Salary.Type permSalary = offer.getSalary()
								.getTypes()
								.stream()
								.filter(type -> type.getType() == SalaryType.PERMANENT)
								.findFirst()
								.orElseThrow();
						return permSalary.getMin() + " - " + permSalary.getMax() + " " + offer.getSalary().getCurrency() + " | " + offer.getTitle();
					})
					.toList();

			resultsAsString.forEach(System.out::println);
		};
	}
}
