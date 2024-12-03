package com.sunny.sunnyfarm.service;

import com.sunny.sunnyfarm.dto.UserLoginDto;

public interface UserService {
    RegistrationResult register(UserLoginDto userLoginDto);
    boolean checkEmail(String email);
    boolean login(UserLoginDto userLoginDto);
}
