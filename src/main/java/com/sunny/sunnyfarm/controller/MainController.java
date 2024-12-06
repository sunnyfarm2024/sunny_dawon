package com.sunny.sunnyfarm.controller;

import com.sunny.sunnyfarm.dto.MainDto;
import com.sunny.sunnyfarm.service.impl.MainServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
public class MainController {

    private final MainServiceImpl mainServiceImpl;

    @GetMapping("/info")
    public ResponseEntity<MainDto> getMainData(HttpSession session){
        Integer userId = (Integer) session.getAttribute("userId");

        MainDto mainData = mainServiceImpl.getMainData(userId);

        return ResponseEntity.ok(mainData);
    }
}
