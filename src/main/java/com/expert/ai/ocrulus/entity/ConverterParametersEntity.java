package com.expert.ai.ocrulus.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ConverterParametersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String engine;
    private String totalLicensePages;
    private String remainingPages;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getTotalLicensePages() {
        return totalLicensePages;
    }

    public void setTotalLicensePages(String totalLicensePages) {
        this.totalLicensePages = totalLicensePages;
    }

    public String getRemainingPages() {
        return remainingPages;
    }

    public void setRemainingPages(String remainingPages) {
        this.remainingPages = remainingPages;
    }
}
