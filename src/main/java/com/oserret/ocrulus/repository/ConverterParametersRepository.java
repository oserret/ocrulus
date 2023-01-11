package com.oserret.ocrulus.repository;

import com.oserret.ocrulus.entity.ConverterParametersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConverterParametersRepository extends JpaRepository<ConverterParametersEntity, Long > {

    ConverterParametersEntity findByEngine(String engine);

}
