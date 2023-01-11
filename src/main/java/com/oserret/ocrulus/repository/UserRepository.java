package com.oserret.ocrulus.repository;

import com.oserret.ocrulus.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long > {
    UserEntity findByUserName(String userName);
    UserEntity findByUserNameAndPassword(String userName, String password);
 }
