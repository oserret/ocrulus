package com.expert.ai.ocrulus.bean;

public class ConverterParametersBean {

    private Long id;

    private String engine;
    private String totalLicensePages;
    private String remainingPages;

    public ConverterParametersBean(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEngine() {
        return engine;
    }

    public String getTotalLicensePages() {
        return totalLicensePages;
    }

    public String getRemainingPages() {
        return remainingPages;
    }

}
