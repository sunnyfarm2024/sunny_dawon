package com.sunny.sunnyfarm.service.impl;

import com.sunny.sunnyfarm.dto.UserLoginDto;
import com.sunny.sunnyfarm.entity.*;
import com.sunny.sunnyfarm.repository.*;
import com.sunny.sunnyfarm.service.RegistrationResult;
import com.sunny.sunnyfarm.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FarmRepository farmRepository;
    private final InventoryRepository inventoryRepository;
    private final UserQuestRepository userQuestRepository;
    private final QuestRepository questRepository;
    private final TitleRepository titleRepository;
    private final UserTitleRepository userTitleRepository;

    // 이메일 중복 체크
    public boolean checkEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    // 비밀번호 암호화
    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    // 회원가입
    public RegistrationResult register(UserLoginDto userLoginDto) {
        if (checkEmail(userLoginDto.getEmail())) {
            return RegistrationResult.DUPLICATE_EMAIL;
        }

        String encryptedPassword = encryptPassword(userLoginDto.getPassword());

        User newUser = new User();
        newUser.setEmail(userLoginDto.getEmail());
        newUser.setPassword(encryptedPassword);

        User userId = userRepository.save(newUser);

        Farm newFarm = new Farm();
        newFarm.setUser(userId);
        farmRepository.save(newFarm);

        for (int i = 1; i <= 15; i++) {
            Inventory inventorySlot = new Inventory();
            inventorySlot.setUser(userId);
            inventorySlot.setSlotNumber(i);
            inventoryRepository.save(inventorySlot);
        }

        for (int i = 1; i <= 12; i++) {
            final int questId = i;

            Quest quest = questRepository.findById(i)
                    .orElseThrow(() -> new IllegalArgumentException("QuestID:" + questId + "를 찾을 수 없습니다."));

            UserQuest userQuest = new UserQuest();
            userQuest.setUser(userId);
            userQuest.setQuest(quest);
            userQuestRepository.save(userQuest);
        }

        for (int i = 1; i <= 11; i++) {
            final int titleId = i;

            Title title = titleRepository.findById(i)
                    .orElseThrow(() -> new IllegalArgumentException("TitleID:" + titleId + "를 찾을 수 없습니다."));

            UserTitle userTitle = new UserTitle();
            userTitle.setUser(userId);
            userTitle.setTitle(title);
            userTitleRepository.save(userTitle);
        }

        return RegistrationResult.SUCCESS;
    }

    // 로그인
    public boolean login(UserLoginDto userLoginDto) {
        User user = userRepository.findByEmail(userLoginDto.getEmail()).orElse(null);

        if (user != null && passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            return true;
        }

        return false;
    }

//    // 구글로 로그인
//    public User googleLogin(String email) {
//        if (checkEmail(email)) {
//            return userRepository.findByEmail(email).orElse(null);
//        } else {
//            UserLoginDto userLoginDto = new UserLoginDto();
//            userLoginDto.setEmail(email);
//            userLoginDto.setPassword(generateRandomPassword()); // 랜덤 비밀번호 설정
//
//            register(userLoginDto);
//
//            // 새로 생성된 사용자 반환
//            return userRepository.findByEmail(email).orElse(null);
//        }
//    }
//
//    // 랜덤 비밀번호 생성
//    private String generateRandomPassword() {
//        int length = 12; // 비밀번호 길이
//        String charPool = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%";
//        StringBuilder password = new StringBuilder();
//
//        Random random = new Random();
//        for (int i = 0; i < length; i++) {
//            int index = random.nextInt(charPool.length());
//            password.append(charPool.charAt(index));
//        }
//
//        return password.toString();
//    }

}