package com.sunny.sunnyfarm.repository;

import com.sunny.sunnyfarm.entity.UserPlant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPlantRepository extends JpaRepository<UserPlant,Integer> {
}
