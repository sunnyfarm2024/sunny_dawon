package com.sunny.sunnyfarm.repository;

import com.sunny.sunnyfarm.entity.UserTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTitleRepository extends JpaRepository<UserTitle,Integer> {
}
