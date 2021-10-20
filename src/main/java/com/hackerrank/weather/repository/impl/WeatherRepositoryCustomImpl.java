package com.hackerrank.weather.repository.impl;

import com.hackerrank.weather.entities.Weather;
import com.hackerrank.weather.model.WeatherSearchCriteria;
import com.hackerrank.weather.output.SearchWeatherJSON;
import com.hackerrank.weather.repository.WeatherRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class WeatherRepositoryCustomImpl implements WeatherRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<SearchWeatherJSON> searchWeathers(WeatherSearchCriteria weatherSearchCriteria) {

        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<SearchWeatherJSON> criteriaQuery = criteriaBuilder.createQuery(SearchWeatherJSON.class);
        final Root<Weather> weatherRoot = criteriaQuery.from(Weather.class);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
