package com.oserret.ocrulus.service;

import com.oserret.ocrulus.bean.ConverterParametersBean;
import com.oserret.ocrulus.entity.ConverterParametersEntity;

public interface ConverterParametersService {

    ConverterParametersEntity findByEngine(String engine);
    ConverterParametersEntity updateConverterParameters(ConverterParametersBean converterParametersBean);
}
