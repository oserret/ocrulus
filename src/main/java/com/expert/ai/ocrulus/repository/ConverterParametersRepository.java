package com.expert.ai.ocrulus.repository;

import com.expert.ai.ocrulus.entity.ConverterParametersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConverterParametersRepository extends JpaRepository<ConverterParametersEntity, Long > {

    ConverterParametersEntity findByEngine(String engine);

}
