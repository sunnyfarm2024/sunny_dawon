package com.sunny.sunnyfarm.repository;

import com.sunny.sunnyfarm.entity.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TitleRepository extends JpaRepository<Title, Integer> {
    @Query("SELECT t.titleName FROM Title t WHERE t.titleId = :titleId")
    Optional<String> findTitleNameById(@Param("titleId") Integer titleId);
}
