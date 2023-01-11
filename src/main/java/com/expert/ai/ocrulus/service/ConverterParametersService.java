package com.expert.ai.ocrulus.service;

import com.expert.ai.ocrulus.bean.ConverterParametersBean;
import com.expert.ai.ocrulus.entity.ConverterParametersEntity;

public interface ConverterParametersService {

    ConverterParametersEntity findByEngine(String engine);
    ConverterParametersEntity updateConverterParameters(ConverterParametersBean converterParametersBean);
}
