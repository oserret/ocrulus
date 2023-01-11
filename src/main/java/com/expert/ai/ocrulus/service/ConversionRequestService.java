package com.expert.ai.ocrulus.service;

import com.expert.ai.ocrulus.bean.ConversionRequestBean;
import com.expert.ai.ocrulus.bean.UpdateConversionRequestBean;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface ConversionRequestService {

    List<ConversionRequestBean> findAll();
    List<ConversionRequestBean> findAllByUserName(String userName);
    List<ConversionRequestBean> findAllByUserNameAndStatus(String userName, String status);
    ConversionRequestBean saveConversionRequest(ConversionRequestBean conversionRequest);
    ConversionRequestBean findNotProcessed();
    ConversionRequestBean findNotProcessedByUserName(String userName);

    ConversionRequestBean findNotProcessedByStatus(String status);

    ConversionRequestBean updateConversionRequest(UpdateConversionRequestBean conversionRequestBean);
    ConversionRequestBean findByUserNameAndId(String userName, Long id);
    void delete(Long id);
    
    @Transactional
    void deleteAll(List<Long> ids);

}
