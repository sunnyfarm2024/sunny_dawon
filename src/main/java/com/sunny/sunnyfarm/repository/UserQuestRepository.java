package com.sunny.sunnyfarm.repository;

import com.sunny.sunnyfarm.entity.UserQuest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserQuestRepository extends JpaRepository<UserQuest,Integer> {
}
