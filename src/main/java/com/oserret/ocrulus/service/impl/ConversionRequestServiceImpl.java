package com.oserret.ocrulus.service.impl;

import com.oserret.ocrulus.bean.ConversionRequestBean;
import com.oserret.ocrulus.bean.UpdateConversionRequestBean;
import com.oserret.ocrulus.entity.ConversionRequestEntity;
import com.oserret.ocrulus.repository.ConversionRequestRepository;
import com.oserret.ocrulus.service.ConversionRequestService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConversionRequestServiceImpl implements ConversionRequestService {

    @Value("${file.upload.rootLocation}")
    private final String filePath;

    @Autowired
    private ConversionRequestRepository conversionRequestRepository;

    public ConversionRequestServiceImpl() {
        this.filePath = null;
    }

    /**
     * Method to return the list of all the conversion requests in the system.
     * @return list of requests
     */
    @Override
    public List<ConversionRequestBean> findAll() {
        List <ConversionRequestBean> requests = new ArrayList< >();
        List <ConversionRequestEntity> conversionRequestEntityList = conversionRequestRepository.findAll();
        conversionRequestEntityList.forEach(ConversionRequestEntity -> {
            requests.add(populateConversionRequestBean(ConversionRequestEntity));
        });
        return requests;
    }
    
    @Override
    public List<ConversionRequestBean> findAllByUserName(String userName) {
        List <ConversionRequestBean> requests = new ArrayList<ConversionRequestBean>();
        List <ConversionRequestEntity> conversionRequestEntityList = conversionRequestRepository.findAllByUserName(userName);
        conversionRequestEntityList.forEach(ConversionRequestEntity -> {
            requests.add(populateConversionRequestBean(ConversionRequestEntity));
        });
        return requests;
    }
    
    @Override
    public ConversionRequestBean findByUserNameAndId(String userName, Long id) {
        
    	ConversionRequestEntity conversionRequestEntity = conversionRequestRepository.findFirstByUserNameAndId(userName, id);
        ConversionRequestBean conversionRequestBean=null;
        
        if(conversionRequestEntity != null)
        	conversionRequestBean = populateConversionRequestBean(conversionRequestEntity);
        
        return conversionRequestBean;
    }
    
    @Override
    public void delete(Long id) {
        
    	conversionRequestRepository.deleteById(id);
        
    }
    
    @Override
    public void deleteAll(List<Long> ids) {
        
    	conversionRequestRepository.deleteByIdIn(ids);
        
    }
    
    @Override
    public List<ConversionRequestBean> findAllByUserNameAndStatus(String userName, String status) {
        List <ConversionRequestBean> requests = new ArrayList<ConversionRequestBean>();
        List <ConversionRequestEntity> conversionRequestEntityList = conversionRequestRepository.findAllByUserNameAndStatus(userName, status);
        conversionRequestEntityList.forEach(ConversionRequestEntity -> {
            requests.add(populateConversionRequestBean(ConversionRequestEntity));
        });
        return requests;
    }

    /**
     * Create a conversion request based on the data sent to the service class.
     * @param conversionRequestBean
     * @return DTO representation of the conversion request
     */
    @Override
    public ConversionRequestBean saveConversionRequest(ConversionRequestBean conversionRequestBean) {
        ConversionRequestEntity conversionRequestEntity = populateConversionRequestEntity(conversionRequestBean);
        try {
            ConversionRequestEntity createdRequest = conversionRequestRepository.save(conversionRequestEntity);
            return populateConversionRequestBean(createdRequest);
        }catch (ConstraintViolationException | DataIntegrityViolationException ex){
            return null;
        }
    }

    @Override
    public ConversionRequestBean findNotProcessed() {
        ConversionRequestBean conversionRequestBean;
        ConversionRequestEntity conversionRequestEntity = conversionRequestRepository.findFirstByProcessedFalse();

        if(conversionRequestEntity != null)
            conversionRequestBean = populateConversionRequestBean(conversionRequestEntity);
        else
            conversionRequestBean = null;

        return conversionRequestBean;
    }
    
    @Override
    public ConversionRequestBean findNotProcessedByUserName(String userName) {
        ConversionRequestBean conversionRequestBean;
        ConversionRequestEntity conversionRequestEntity = conversionRequestRepository.findFirstByProcessedFalseAndUserName(userName);

        if(conversionRequestEntity != null)
            conversionRequestBean = populateConversionRequestBean(conversionRequestEntity);
        else
            conversionRequestBean = null;

        return conversionRequestBean;
    }

    @Override
    public ConversionRequestBean findNotProcessedByStatus(String status) {
        ConversionRequestBean conversionRequestBean;
        ConversionRequestEntity conversionRequestEntity = conversionRequestRepository.findFirstByProcessedFalseAndAndStatus(status);

        if(conversionRequestEntity != null)
            conversionRequestBean = populateConversionRequestBean(conversionRequestEntity);
        else
            conversionRequestBean = null;

        return conversionRequestBean;
    }

    
    @Override
    public ConversionRequestBean updateConversionRequest(UpdateConversionRequestBean updateConversionRequestBean) {

        Optional<ConversionRequestEntity> conversionRequestEntity = conversionRequestRepository.findById(updateConversionRequestBean.getId());

        conversionRequestEntity.get().setPathOutput(updateConversionRequestBean.getPathOutput());
        conversionRequestEntity.get().setStatus(updateConversionRequestBean.getStatus());
        conversionRequestEntity.get().setProcessed(updateConversionRequestBean.getProcessed());

        try {
            ConversionRequestEntity createdRequest = conversionRequestRepository.save(conversionRequestEntity.get());
            return populateConversionRequestBean(createdRequest);
        }catch (ConstraintViolationException | DataIntegrityViolationException ex){
            return null;
        }
    }

    /**
     * Internal method to convert ConversionRequest JPA entity to the DTO object
     * for frontend data
     * @param conversionRequestEntity
     * @return ConversionRequestData
     */
    private ConversionRequestBean populateConversionRequestBean(final ConversionRequestEntity conversionRequestEntity) {
        ConversionRequestBean conversionRequestBean = new ConversionRequestBean();
        conversionRequestBean.setId(conversionRequestEntity.getId());
        conversionRequestBean.setConversionEngine(conversionRequestEntity.getConversionEngine());
        conversionRequestBean.setFileName(conversionRequestEntity.getFileName());
        conversionRequestBean.setProcessed(conversionRequestEntity.getProcessed());
        conversionRequestBean.setUserName(conversionRequestEntity.getUserName());
        conversionRequestBean.setPath(conversionRequestEntity.getPath());
        conversionRequestBean.setStatus(conversionRequestEntity.getStatus());
        conversionRequestBean.setEmailTo(conversionRequestEntity.getEmailTo());
        conversionRequestBean.setPathOutput(conversionRequestEntity.getPathOutput());
        conversionRequestBean.setLanguage(conversionRequestEntity.getLanguage());
        conversionRequestBean.setExtractionMode(conversionRequestEntity.getExtractionMode());
        return conversionRequestBean;
    }

    /**
     * Method to map the front end ConversionRequest object to the JPA ConversionRequest entity.
     * @param conversionRequestBean
     * @return ConversionRequestEntity
     */
    private ConversionRequestEntity populateConversionRequestEntity(ConversionRequestBean conversionRequestBean) {
        ConversionRequestEntity conversionRequestEntity = new ConversionRequestEntity();
        conversionRequestEntity.setFileName(conversionRequestBean.getFileName());
        conversionRequestEntity.setConversionEngine(conversionRequestBean.getConversionEngine());
        conversionRequestEntity.setUserName(conversionRequestBean.getUserName());
        conversionRequestEntity.setProcessed(conversionRequestBean.getProcessed());
        conversionRequestEntity.setPath(this.filePath);
        conversionRequestEntity.setEmailTo(conversionRequestBean.getEmailTo());
        conversionRequestEntity.setLanguage(conversionRequestBean.getLanguage());
        conversionRequestEntity.setExtractionMode(conversionRequestBean.getExtractionMode());
        return conversionRequestEntity;
    }

}
