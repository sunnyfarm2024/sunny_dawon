package com.sunny.sunnyfarm.controller;

import com.sunny.sunnyfarm.dto.UserLoginDto;
import com.sunny.sunnyfarm.service.RegistrationResult;
import com.sunny.sunnyfarm.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserLoginDto userLoginDto) {
        RegistrationResult result = userService.register(userLoginDto);

        switch (result) {
            case SUCCESS:
                return ResponseEntity.ok("회원가입이 성공적으로 완료되었습니다.");
            case DUPLICATE_EMAIL:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 사용중인 이메일입니다.");
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 처리 중 오류가 발생했습니다.");
        }
    }

    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
        boolean exists = userService.checkEmail(email);
        return ResponseEntity.ok(exists);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDto userLoginDto) {
        boolean isAuthenticated = userService.login(userLoginDto);

        if (isAuthenticated) {
            return ResponseEntity.ok("로그인에 성공하였습니다.");
        } else {
            return ResponseEntity.status(401).body("이메일 또는 비밀번호가 올바르지 않습니다.");
        }
    }
}
