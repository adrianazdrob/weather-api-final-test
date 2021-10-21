package com.hackerrank.weather.repository.impl;

import com.hackerrank.weather.entities.Weather;
import com.hackerrank.weather.model.WeatherSearchCriteria;
import com.hackerrank.weather.output.SearchWeatherJSON;
import com.hackerrank.weather.repository.WeatherRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WeatherRepositoryCustomImpl implements WeatherRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<SearchWeatherJSON> searchWeathers(WeatherSearchCriteria weatherSearchCriteria) {

        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<SearchWeatherJSON> criteriaQuery = criteriaBuilder.createQuery(SearchWeatherJSON.class);
        final Root<Weather> weatherRoot = criteriaQuery.from(Weather.class);


        List<Predicate> predicates = getFilterPredicates(weatherSearchCriteria,criteriaBuilder,weatherRoot);
        if(!predicates.isEmpty()) {
            criteriaQuery.where(predicates.toArray(new Predicate[]{}));
        }

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    private List<Predicate> getFilterPredicates(WeatherSearchCriteria weatherSearchCriteria, CriteriaBuilder criteriaBuilder, Root<Weather>weatherRoot) {
        Predicate dateCondition = null;

        if(weatherSearchCriteria.getDate() != null){
            dateCondition = criteriaBuilder.equal(weatherRoot.get("date"), weatherSearchCriteria.getDate());
        }

        return Stream.of(dateCondition)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
