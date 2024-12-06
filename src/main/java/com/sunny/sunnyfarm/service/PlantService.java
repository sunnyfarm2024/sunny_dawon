package com.sunny.sunnyfarm.service;

import com.sunny.sunnyfarm.dto.PlantDto;

import java.util.List;

public interface PlantService {
    List<PlantDto> getPlant(Integer farmId);
}
