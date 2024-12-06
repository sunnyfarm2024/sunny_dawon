package com.sunny.sunnyfarm.controller;

import com.sunny.sunnyfarm.dto.UserDto;
import com.sunny.sunnyfarm.dto.UserLoginDto;
import com.sunny.sunnyfarm.service.CheckResult;
import com.sunny.sunnyfarm.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
        boolean exists = userService.checkEmail(email);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/check-username")
    public ResponseEntity<Boolean> checkUserName(@RequestParam String userName) {
        boolean exists = userService.checkUserName(userName);
        return ResponseEntity.ok(exists);
    }

    @PostMapping("/register")
    public ResponseEntity<CheckResult> register(@RequestBody UserLoginDto userLoginDto) {
        CheckResult result = userService.register(userLoginDto);

        return switch (result) {
            case SUCCESS -> ResponseEntity.ok(CheckResult.SUCCESS);
            case DUPLICATE -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CheckResult.DUPLICATE);
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(CheckResult.FAIL);
        };
    }

    @PostMapping("/login")
    public ResponseEntity<CheckResult> login(@RequestBody UserLoginDto userLoginDto, HttpSession session) {
        CheckResult result = userService.login(userLoginDto);

        if (result == CheckResult.SUCCESS) {
            // 세션에 userId 저장
            Integer userId = userService.getUserIdByEmail(userLoginDto.getEmail());
            session.setAttribute("userId", userId);
            return ResponseEntity.ok(CheckResult.SUCCESS);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CheckResult.FAIL);
        }
    }

    @PostMapping("/username")
    public ResponseEntity<CheckResult>  updateUserName(@RequestBody UserDto userDto, HttpSession session) {

        Integer userId = (Integer) session.getAttribute("userId"); // 세션에서 userId 가져오기

        String username = userDto.getUserName();
        CheckResult result = userService.updateUserName(userId, username);

        return switch (result) {
            case SUCCESS -> ResponseEntity.ok(CheckResult.SUCCESS);
            case DUPLICATE -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CheckResult.DUPLICATE);
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(CheckResult.FAIL);
        };
    }
}
