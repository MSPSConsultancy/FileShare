package com.fileshare.repository;

import com.fileshare.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepositoryTest extends JpaRepository<UserEntity,Integer> {



    @Query("SELECT t FROM UserEntity t WHERE t.userName = :userName")
    UserEntity findByUserName(@Param("userName")String userName);

}
