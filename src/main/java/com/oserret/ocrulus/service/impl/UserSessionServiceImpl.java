package com.oserret.ocrulus.service.impl;

import com.oserret.ocrulus.entity.UserSessionEntity;
import com.oserret.ocrulus.repository.UserSessionRepository;
import com.oserret.ocrulus.service.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserSessionServiceImpl implements UserSessionService {

    @Autowired
    private UserSessionRepository userSessionRepository;


    @Override
    public void save(UserSessionEntity userSessionEntity) {
        userSessionRepository.save(userSessionEntity);
    }
}
