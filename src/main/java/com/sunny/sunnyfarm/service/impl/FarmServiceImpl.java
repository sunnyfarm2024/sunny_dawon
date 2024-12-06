package com.sunny.sunnyfarm.service.impl;

import com.sunny.sunnyfarm.dto.FarmDto;
import com.sunny.sunnyfarm.entity.Farm;
import com.sunny.sunnyfarm.repository.FarmRepository;
import com.sunny.sunnyfarm.repository.ShopRepository;
import com.sunny.sunnyfarm.service.FarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FarmServiceImpl implements FarmService {

    private final FarmRepository farmRepository;
    private final ShopRepository shopRepository;

    public FarmDto getFarm(Integer farmId) {
        Farm farm = farmRepository.findById(farmId)
                .orElseThrow(() -> new IllegalArgumentException("농장을 찾을 수 없습니다. ID: " + farmId));

        String signImage = null;
        if (farm.getSign() != null) {
            signImage = shopRepository.findImageUrlByItemId(farm.getSign().getItemId())
                    .orElse(null);
        }

        String cornerImage = null;
        if (farm.getCorner() != null) {
            cornerImage = shopRepository.findImageUrlByItemId(farm.getCorner().getItemId())
                    .orElse(null);
        }

        return new FarmDto(signImage, cornerImage, farm.getFarmDescription());
    }
}
