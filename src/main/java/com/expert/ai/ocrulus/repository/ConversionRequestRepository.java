package com.expert.ai.ocrulus.repository;

import com.expert.ai.ocrulus.entity.ConversionRequestEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversionRequestRepository extends JpaRepository<ConversionRequestEntity, Long > {
    
	ConversionRequestEntity findFirstByProcessedFalse();
	ConversionRequestEntity findFirstByProcessedFalseAndUserName(String userName);
	ConversionRequestEntity findFirstByUserNameAndId(String userName, Long id);
	
	void deleteById(Long id);
	void deleteByIdIn(List<Long> ids);

	List<ConversionRequestEntity> findAllByUserName(String userName);
	List<ConversionRequestEntity> findAllByUserNameAndStatus(String userName, String status);


	ConversionRequestEntity findFirstByProcessedFalseAndAndStatus(String status);
}
