package com.sunny.sunnyfarm.repository;

import com.sunny.sunnyfarm.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Integer> {
    @Query("SELECT s.itemImageUrl FROM Shop s WHERE s.itemId = :itemId")
    Optional<String> findImageUrlByItemId(@Param("itemId") Integer itemId);
}