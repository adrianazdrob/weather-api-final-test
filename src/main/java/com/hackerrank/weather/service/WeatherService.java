package com.hackerrank.weather.service;

import com.hackerrank.weather.entities.Weather;
import com.hackerrank.weather.mapper.WeatherMapper;
import com.hackerrank.weather.model.WeatherInput;
import com.hackerrank.weather.model.WeatherSearchCriteria;
import com.hackerrank.weather.output.WeatherJSON;
import com.hackerrank.weather.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeatherService {

    private final WeatherRepository weatherRepository;

    @Autowired
    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    @Transactional
    public WeatherJSON newWeather(WeatherInput weatherInput) {
        Weather weather = WeatherMapper.inputToWeather(weatherInput);

        weatherRepository.save(weather);

        return WeatherMapper.weatherToJSON(weather);
    }

    public WeatherJSON getWeatherById(Integer id) {

       Weather weather = weatherRepository.findById(id)
               .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no record in the collection with this id"));

       return WeatherMapper.weatherToJSON(weather);
    }

    public List<WeatherJSON> getWeather(WeatherSearchCriteria weatherSearchCriteria) {
        if(weatherSearchCriteria.getDate() != null) {
            List<Weather> dateList = weatherRepository.findAll(isMatchingDate(weatherSearchCriteria));
            return dateList.stream().map(weather -> WeatherMapper.weatherToJSON(weather)).collect(Collectors.toList());
        } else{
            return weatherRepository.findAll().stream().map(weather -> WeatherMapper.weatherToJSON(weather)).collect(Collectors.toList());
        }
    }

    private Specification<Weather> isMatchingDate(WeatherSearchCriteria weatherSearchCriteria) {
        return (weatherRoot, criteriaQuery, builder) -> builder.equal(weatherRoot.get("date"), LocalDate.parse(weatherSearchCriteria.getDate()));
    }
}
