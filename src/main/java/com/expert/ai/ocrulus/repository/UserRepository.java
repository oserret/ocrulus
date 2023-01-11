package com.expert.ai.ocrulus.repository;

import com.expert.ai.ocrulus.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long > {
    UserEntity findByUserName(String userName);
    UserEntity findByUserNameAndPassword(String userName, String password);
 }
