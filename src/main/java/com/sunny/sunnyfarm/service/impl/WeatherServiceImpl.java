package com.sunny.sunnyfarm.service.impl;

import com.sunny.sunnyfarm.service.WeatherServise;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherServise {

    private static final String API_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0";
    @Value("${weather.api-key}")
    private String apiKey;

    public void printApiKey() {
        System.out.println("API Key: " + apiKey);
    }
}
