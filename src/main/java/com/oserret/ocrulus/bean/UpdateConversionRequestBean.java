package com.oserret.ocrulus.bean;

public class UpdateConversionRequestBean {

    private Long id;
    private String pathOutput;
    private Boolean processed = Boolean.FALSE;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPathOutput() {
        return pathOutput;
    }

    public void setPathOutput(String pathOutput) {
        this.pathOutput = pathOutput;
    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
