package com.hackerrank.weather.repository;

import com.hackerrank.weather.model.WeatherSearchCriteria;
import com.hackerrank.weather.output.SearchWeatherJSON;

import java.util.List;

public interface WeatherRepositoryCustom {

    List<SearchWeatherJSON> searchWeathers(WeatherSearchCriteria weatherSearchCriteria);
}
