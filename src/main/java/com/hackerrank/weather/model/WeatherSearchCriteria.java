package com.hackerrank.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hackerrank.weather.utils.Patterns;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class WeatherSearchCriteria {

    @Schema(example = "2019-06-11")
    @Pattern(regexp = Patterns.YYYY_MM_DD_REGEXP, message = "Date field must match the following pattern: 'yyyy-mm-dd'")
    private String date;

    private String city;
    private String sort;

    @JsonIgnore
    public boolean isEmpty() {
        return date == null
                && city == null
                && sort == null;
    }
}
