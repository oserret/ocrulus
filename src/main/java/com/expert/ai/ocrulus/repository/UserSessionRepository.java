package com.expert.ai.ocrulus.repository;

import com.expert.ai.ocrulus.entity.UserSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSessionEntity, Long > {
}
