package com.oserret.ocrulus.service;

import com.oserret.ocrulus.bean.LoginRequestBean;
import com.oserret.ocrulus.bean.UserBean;
import com.oserret.ocrulus.entity.UserEntity;

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
