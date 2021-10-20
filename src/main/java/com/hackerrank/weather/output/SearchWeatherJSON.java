package com.hackerrank.weather.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class SearchWeatherJSON {

    private String date;
    private String city;
    private String sort;
}
