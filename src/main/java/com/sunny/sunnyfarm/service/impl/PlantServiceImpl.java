package com.sunny.sunnyfarm.service.impl;

import com.sunny.sunnyfarm.dto.PlantDto;
import com.sunny.sunnyfarm.entity.*;
import com.sunny.sunnyfarm.repository.*;
import com.sunny.sunnyfarm.service.PlantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PlantServiceImpl implements PlantService {
    private final FarmRepository farmRepository;
    private final PlantRepository plantRepository;

    public List<PlantDto> getPlant(Integer farmId){
        Farm farm = farmRepository.findById(farmId)
                .orElseThrow(() -> new IllegalArgumentException("농장을 찾을 수 없습니다. ID: " + farmId));

        List<UserPlant> userPlants = Stream.of(
                farm.getLeftPlant(),
                farm.getCenterPlant(),
                farm.getRightPlant()
        ).filter(Objects::nonNull).toList(); // Null 값 제외

        List<PlantDto> plantDtos = new ArrayList<>();

        for (UserPlant userPlant : userPlants) {
            Plant plant = plantRepository.findById(userPlant.getPlant().getPlantId())
                    .orElseThrow(() -> new IllegalArgumentException("식물을 찾을 수 없습니다. ID: " + userPlant.getPlant().getPlantId()));

            String plantLocation = findPlantLocation(farm, userPlant);
            String plantImage = findPlantImage(userPlant, plant);

            PlantDto plantDto = new PlantDto(
                    userPlant.getPlantName(),
                    plant.getPlantType().name(),
                    userPlant.getGrowthStage().name(),
                    String.valueOf(userPlant.getGrowthProgress()),
                    userPlant.getWaterLevel(),
                    userPlant.getLivesLeft(),
                    plantLocation,
                    plantImage,
                    plant.getDifficulty().name(),
                    userPlant.getFertilizerEndsAt()
            );
            plantDtos.add(plantDto);
        }
        return plantDtos;
    }

    private String findPlantLocation(Farm farm, UserPlant userPlant) {
        if (userPlant == farm.getLeftPlant()) {
            return "left";
        } else if (userPlant == farm.getCenterPlant()) {
            return "center";
        } else if (userPlant == farm.getRightPlant()) {
            return "right";
        }
        return null;
    }

    private String findPlantImage(UserPlant userPlant, Plant plant) {
        if (userPlant.getLivesLeft() == 0) {
            return plant.getDeadImage();
        } else {
            return switch (userPlant.getGrowthStage()) {
                case LEVEL1 -> plant.getLevel1Image();
                case LEVEL2 -> plant.getLevel2Image();
                case LEVEL3 -> plant.getLevel3Image();
                case MAX -> plant.getMaxImage();
            };
        }
    }
}
