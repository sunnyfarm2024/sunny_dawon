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

import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<String> register(@RequestBody UserLoginDto userLoginDto) {
        CheckResult result = userService.register(userLoginDto);

        return switch (result) {
            case SUCCESS -> ResponseEntity.ok("회원가입이 성공적으로 완료되었습니다.");
            case DUPLICATE -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 사용중인 이메일입니다.");
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 처리 중 오류가 발생했습니다.");
        };
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserLoginDto userLoginDto, HttpSession session) {
        UserDto userDto = userService.login(userLoginDto);
        Map<String, Object> response = new HashMap<>();

        if (userDto.isSuccess()) {
            // 세션에 userId 저장
            Integer userId = userService.getUserIdByEmail(userLoginDto.getEmail());
            session.setAttribute("userId", userId);

            response.put("success", true);
            response.put("username", userDto.getUsername());
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "이메일 또는 비밀번호가 올바르지 않습니다.");
            return ResponseEntity.status(401).body(response);
        }
    }

    @PostMapping("/username")
    public ResponseEntity<String> updateUserName(@RequestBody UserDto userDto, HttpSession session) {

        Integer userId = (Integer) session.getAttribute("userId"); // 세션에서 userId 가져오기

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        String username = userDto.getUsername();
        CheckResult result = userService.updateUserName(userId, username);

        return switch (result) {
            case SUCCESS -> ResponseEntity.ok("닉네임 변경이 성공적으로 완료되었습니다.");
            case DUPLICATE -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 사용중인 닉네임입니다.");
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("닉네임 변경 중 오류가 발생했습니다.");
        };
    }
}
