package com.sunny.sunnyfarm.repository;

import com.sunny.sunnyfarm.entity.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TitleRepository extends JpaRepository<Title, Integer> {
}
