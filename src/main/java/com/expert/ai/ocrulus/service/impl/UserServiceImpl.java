package com.expert.ai.ocrulus.service.impl;

import com.expert.ai.ocrulus.bean.LoginRequestBean;
import com.expert.ai.ocrulus.bean.UserBean;
import com.expert.ai.ocrulus.entity.UserEntity;
import com.expert.ai.ocrulus.repository.UserRepository;
import com.expert.ai.ocrulus.service.UserService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service

public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    /**
     * Create a customer based on the data sent to the service class.
     * @param user
     * @return DTO representation of the customer
     */
    @Override
    public UserBean saveUser(UserBean user) {
        UserEntity userEntity = populateUserEntity(user);
        try {
            UserEntity createdUser = userRepository.save(userEntity);
            return populateUserBean(createdUser);
        }catch (ConstraintViolationException | DataIntegrityViolationException ex){
            return null;
        }
    }

    @Override
    public UserEntity login(LoginRequestBean loginRequestBean) {
        UserEntity userLogued = userRepository.findByUserNameAndPassword(loginRequestBean.getUserName(), bCryptPasswordEncoder.encode(loginRequestBean.getPassword()));
        return userLogued;
    }

    @Override
    public Optional<UserEntity> load(Long userId) {
        Optional<UserEntity> userLoaded = userRepository.findById(userId);
        return userLoaded;
    }

    @Override
    public UserEntity loadUserByUsername(String userName) {
        return userRepository.findByUserName(userName);
    }


    @Override
    public String save(UserEntity user) {
        userRepository.save(user);
        return "user created";
    }

    /**
     * Method to return the list of all the users in the system.
     * @return list of users
     */
    @Override
    public List <UserBean> findAll() {
        List <UserBean> users = new ArrayList < > ();
        List <UserEntity> userEntityList = userRepository.findAll();
        userEntityList.forEach(userEntity -> {
                users.add(populateUserBean(userEntity));
        });
        return users;
    }


    /**
     * Internal method to convert User JPA entity to the DTO object
     * for frontend data
     * @param userEntity
     * @return UserData
     */
    private UserBean populateUserBean(final UserEntity userEntity) {
        UserBean userBean = new UserBean();
        userBean.setId(userEntity.getId());
        userBean.setFirstName(userEntity.getFirstName());
        userBean.setLastName(userEntity.getLastName());
        userBean.setEmail(userEntity.getEmail());
        userBean.setUserName(userEntity.getUserName());
        userBean.setPassword(userEntity.getPassword());
        userBean.setStartDate(userEntity.getStartDate());
        userBean.setEndDate(userEntity.getEndDate());
        return userBean;
    }

    /**
     * Method to map the front end user object to the JPA customer entity.
     * @param userBean
     * @return UserEntity
     */
    private UserEntity populateUserEntity(UserBean userBean) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(userBean.getFirstName());
        userEntity.setLastName(userBean.getLastName());
        userEntity.setEmail(userBean.getEmail());
        userEntity.setUserName(userBean.getUserName());
        userEntity.setPassword(userBean.getPassword());
        userEntity.setStartDate(new Date());
        userEntity.setEndDate(null);
        return userEntity;
    }

}
