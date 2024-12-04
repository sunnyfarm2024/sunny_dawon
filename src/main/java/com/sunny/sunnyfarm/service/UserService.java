package com.sunny.sunnyfarm.service;

import com.sunny.sunnyfarm.dto.UserDto;
import com.sunny.sunnyfarm.dto.UserLoginDto;

public interface UserService {
    boolean checkEmail(String email);
    boolean checkUserName(String userName);
    CheckResult register(UserLoginDto userLoginDto);
    UserDto login(UserLoginDto userLoginDto);
    CheckResult updateUserName(int userId, String userName);
    Integer getUserIdByEmail(String email);
}
