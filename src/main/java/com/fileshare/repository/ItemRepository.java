package com.fileshare.repository;

import com.fileshare.model.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ItemRepository extends BaseDAOImpl<ItemEntity> {
	

}
