package com.expert.ai.ocrulus.service.impl;

import com.expert.ai.ocrulus.entity.UserSessionEntity;
import com.expert.ai.ocrulus.repository.UserSessionRepository;
import com.expert.ai.ocrulus.service.UserSessionService;
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
