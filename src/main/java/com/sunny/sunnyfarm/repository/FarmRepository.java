package com.sunny.sunnyfarm.repository;

import com.sunny.sunnyfarm.entity.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Integer> {
}
