package com.expert.ai.ocrulus.controller;

import ch.qos.logback.classic.Logger;
import com.expert.ai.ocrulus.bean.Message;
import com.expert.ai.ocrulus.service.StorageService;
import com.expert.ai.ocrulus.utils.Globals;
import com.expert.ai.ocrulus.utils.StorageFileNotFoundException;
import com.expert.ai.ocrulus.utils.Utilities;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.util.stream.Collectors;

@RestController
@CrossOrigin()
@RequestMapping("/api/files")
public class FileUploadController
{

	private final StorageService storageService;
	Logger logger = (Logger) LoggerFactory.getLogger(FileUploadController.class);

	@Autowired
	public FileUploadController(StorageService storageService)
	{
		this.storageService = storageService;
	}

	@GetMapping("/")
	public String listUploadedFiles(Model model) throws IOException
	{

		model.addAttribute("files", storageService.loadAll().map(path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString()).build().toUri().toString()).collect(Collectors.toList()));

		return Utilities.toJson(new Message("uploadForm"));
	}

	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename)
	{

		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

	@PostMapping("/upload")
	public String handleFileUpload(@RequestParam("file") MultipartFile file) throws JsonProcessingException
	{

		logger.info("Call: /upload SERVICE");
		storageService.store(file);

		return Utilities.toJson(new Message(Globals.FILE_UPLOADED_SUCCESSFULLY + file.getOriginalFilename(), Globals.STATUS_OK));

	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc)
	{
		return ResponseEntity.notFound().build();
	}

}
