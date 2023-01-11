package com.expert.ai.ocrulus.service;

import com.expert.ai.ocrulus.bean.LoginRequestBean;
import com.expert.ai.ocrulus.bean.UserBean;
import com.expert.ai.ocrulus.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    String save(UserEntity user);
    List<UserBean> findAll();
    UserBean saveUser(UserBean user);
    UserEntity login(LoginRequestBean loginRequestBean);
    Optional<UserEntity> load(Long userId);
    UserEntity loadUserByUsername(String userName);
}
