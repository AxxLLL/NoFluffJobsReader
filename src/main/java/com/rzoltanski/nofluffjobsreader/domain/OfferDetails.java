package com.rzoltanski.nofluffjobsreader.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rzoltanski.nofluffjobsreader.domain.enumeration.Category;
import com.rzoltanski.nofluffjobsreader.domain.enumeration.Currency;
import com.rzoltanski.nofluffjobsreader.domain.enumeration.Employment;
import com.rzoltanski.nofluffjobsreader.domain.enumeration.OfferStatus;
import com.rzoltanski.nofluffjobsreader.domain.enumeration.Seniority;
import com.rzoltanski.nofluffjobsreader.domain.enumeration.Technology;
import io.micrometer.common.util.StringUtils;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Data
public class OfferDetails {

    private String id;

    private String title;

    private String companyName;

    private List<Seniority> seniority;

    private Category category;

    private Technology technology;

    private List<Location> locations;

    private Salary salary;

    private String postingUrl;

    private OfferStatus status;

    private LocalDateTime posted;

    private Integer postedOrRenewedDaysAgo;

    @JsonProperty("company")
    private void unpackCompany(Map<String, Object> company) {
        this.companyName = (String) company.get("name");
    }

    @JsonProperty("posted")
    private void unpackPosted(Long posted) {
        this.posted = LocalDateTime.ofInstant(Instant.ofEpochMilli(posted), TimeZone.getDefault().toZoneId());
    }

    @JsonProperty("basics")
    private void unpackBasics(Map<String, Object> basics) {

        this.category = Category.getByName((String) basics.get("category"));

        if (StringUtils.isNotBlank((String) basics.get("technology"))) {
            this.technology = Technology.getByName((String) basics.get("technology"));
        }

        this.seniority = ((List<String>) basics.get("seniority"))
                .stream()
                .map(seniority -> Seniority.getByName(seniority))
                .collect(Collectors.toList());
    }

    @JsonProperty("location")
    private void unpackLocation(Map<String, Object> location) {
        this.locations = ((List<LinkedHashMap<String, Object>>) location.get("places"))
                .stream()
                .map(place -> Location.builder()
                        .city((String) place.get("city"))
                        .country(Optional.ofNullable(place.get("country"))
                                .map(country -> ((LinkedHashMap<String, String>) country).get("name"))
                                .orElse(null))
                        .build()
                )
                .toList();
    }

    @JsonProperty("essentials")
    private void unpackSalary(Map<String, Object> essentials) {
        LinkedHashMap<String, Object> originalSalary = (LinkedHashMap<String, Object>) essentials.get("originalSalary");

        Salary salary = new Salary();
        salary.setCurrency(Currency.valueOf((String) originalSalary.get("currency")));

        List<Salary.Type> types = ((LinkedHashMap<String, LinkedHashMap<String, Object>>) originalSalary.get("types"))
                .entrySet()
                .stream()
                .map(entry -> Salary.Type.builder()
                        .employment(Employment.getByName(entry.getKey()))
                        .period((String) entry.getValue().get("period"))
                        .paidHoliday((boolean) Optional.ofNullable(entry.getValue().get("paidHoliday")).orElse(false))
                        .min(((List<Integer>) entry.getValue().get("range")).get(0))
                        .max(((List<Integer>) entry.getValue().get("range")).get(1))
                        .build()
                )
                .collect(Collectors.toList());
        salary.setTypes(types);

        this.salary = salary;
    }

    @Data
    @Builder
    public static class Location {
        private String city;
        private String country;
    }

    @Data
    public static class Salary {

        private Currency currency;
        private List<Type> types;

        @Builder
        @Data
        public static class Type {
            private Employment employment;
            private String period;
            private boolean paidHoliday;
            private Integer min;
            private Integer max;
        }

        public Type getSalaryByEmploymentType(Employment employment) {
            return this.types.stream()
                    .filter(salary -> salary.getEmployment() == employment)
                    .findFirst()
                    .orElseThrow();
        }
    }
}
