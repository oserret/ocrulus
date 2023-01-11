package com.oserret.ocrulus.service;

import com.oserret.ocrulus.utils.StorageFileNotFoundException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    void store(MultipartFile file);

    void store(Workbook excelFile);

    Stream<Path> loadAll();

    Path load(String filename);

    Path outputPath();

    Resource loadAsResource(String filename) throws StorageFileNotFoundException;

    void deleteAll();

}
