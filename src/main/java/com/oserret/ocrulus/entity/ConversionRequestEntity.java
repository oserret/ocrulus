package com.oserret.ocrulus.entity;

import com.oserret.ocrulus.utils.Globals;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ConversionRequestEntity
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String fileName;
	private String userName;
	private String conversionEngine;
	private Boolean processed = Boolean.FALSE;
	private String path;
	private String status;
	private String emailTo;
	private String pathOutput;
	private String language;
	private String extractionMode;

	public ConversionRequestEntity()
	{
		this.setStatus(Globals.PROCESS_STATUS_NOT_PROCESSED);
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getConversionEngine()
	{
		return conversionEngine;
	}

	public void setConversionEngine(String conversionEngine)
	{
		this.conversionEngine = conversionEngine;
	}

	public Boolean getProcessed()
	{
		return processed;
	}

	public void setProcessed(Boolean processed)
	{
		this.processed = processed;
	}

	public String getPath()
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getEmailTo()
	{
		return emailTo;
	}

	public void setEmailTo(String emailTo)
	{
		this.emailTo = emailTo;
	}

	public String getPathOutput()
	{
		return pathOutput;
	}

	public void setPathOutput(String pathOutput)
	{
		this.pathOutput = pathOutput;
	}

	public String getLanguage()
	{
		return language;
	}

	public void setLanguage(String language)
	{
		this.language = language;
	}

	public String getExtractionMode()
	{
		return extractionMode;
	}

	public void setExtractionMode(String extractionMode)
	{
		this.extractionMode = extractionMode;
	}

}
