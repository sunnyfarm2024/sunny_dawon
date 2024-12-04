package com.sunny.sunnyfarm.repository;

import com.sunny.sunnyfarm.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUserName(String userName);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.selectedTitle = :titleId WHERE u.userId = :userId")
    void updateSelectedTitleId(@Param("userId") Integer userId, @Param("selectedTitle") Integer titleId);
}
