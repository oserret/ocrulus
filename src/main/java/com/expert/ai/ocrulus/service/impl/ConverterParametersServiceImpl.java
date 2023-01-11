package com.expert.ai.ocrulus.service.impl;

import com.expert.ai.ocrulus.bean.ConverterParametersBean;
import com.expert.ai.ocrulus.entity.ConverterParametersEntity;
import com.expert.ai.ocrulus.repository.ConverterParametersRepository;
import com.expert.ai.ocrulus.service.ConverterParametersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConverterParametersServiceImpl implements ConverterParametersService {

    @Autowired
    ConverterParametersRepository converterParametersRepository;

    @Override
    public ConverterParametersEntity findByEngine(String engine) {
        ConverterParametersEntity parameters;

        parameters = converterParametersRepository.findByEngine(engine);

        return parameters;
    }

    @Override
    public ConverterParametersEntity updateConverterParameters(ConverterParametersBean converterParametersBean) {

        ConverterParametersEntity converterParametersEntity =converterParametersRepository.findByEngine(converterParametersBean.getEngine());

        if (converterParametersEntity != null) {
            converterParametersEntity.setRemainingPages(converterParametersBean.getRemainingPages());
        } else {
            converterParametersEntity = new ConverterParametersEntity();
            converterParametersEntity.setRemainingPages(converterParametersBean.getRemainingPages());
            converterParametersEntity.setEngine(converterParametersBean.getEngine());
            converterParametersEntity.setTotalLicensePages(converterParametersBean.getTotalLicensePages());
        }

        converterParametersRepository.save(converterParametersEntity);

        return converterParametersEntity;
    }
}
