package com.fileshare.repository;

import com.fileshare.model.ItemEntity;
import com.fileshare.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepositoryTest extends JpaRepository<ItemEntity,Integer> {



    @Query("SELECT t FROM ItemEntity t WHERE t.itemName = :itemName")
    ItemEntity findByItemName(@Param("itemName") String itemName);

}
