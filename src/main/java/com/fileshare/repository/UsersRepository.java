package com.fileshare.repository;

import com.fileshare.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public class UsersRepository extends BaseDAOImpl<UserEntity> {




}
