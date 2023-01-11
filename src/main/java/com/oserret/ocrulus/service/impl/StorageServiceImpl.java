package com.oserret.ocrulus.service.impl;

import com.oserret.ocrulus.service.StorageService;
import com.oserret.ocrulus.utils.StorageException;
import com.oserret.ocrulus.utils.StorageFileNotFoundException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class StorageServiceImpl implements StorageService {
    @Value("${file.upload.rootLocation}")
    private final String rootLocationS;
    @Value("${file.upload.outputLocation}")
    private final String outputLocationS;

    public StorageServiceImpl() {
        this.rootLocationS = null;
        this.outputLocationS = null;
    }


    @Override
    public void store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            Path destinationFile = Paths.get(this.rootLocationS).resolve(
                    Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(Paths.get(this.rootLocationS).toAbsolutePath())) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    @Override
    public void store(Workbook excelFile) {
        try {
            if (excelFile == null) {
                throw new StorageException("Failed to store empty file.");
            }
            Path destinationFile = Paths.get(this.outputLocationS).resolve(
                    Paths.get(excelFile.getSheetName(0)+"_output"+ ".xlsx"))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(Paths.get(this.outputLocationS).toAbsolutePath())) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }
            try (FileOutputStream output =
                         new FileOutputStream(destinationFile.toString())) {
                excelFile.write(output);
                output.close();
            }
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    // write doc to output stream
    private static void writeXml(Document doc,
                                 OutputStream output)
            throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        // pretty print XML
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);

    }


    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(Paths.get(this.rootLocationS), 1)
                    .filter(path -> !path.equals(Paths.get(this.rootLocationS)))
                    .map(Paths.get(this.rootLocationS)::relativize);
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return Paths.get(this.rootLocationS).resolve(filename);
    }

    @Override
    public Path outputPath() {
        return Paths.get(this.outputLocationS);
    }

    @Override
    public Resource loadAsResource(String filename) throws StorageFileNotFoundException {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(Paths.get(this.rootLocationS).toFile());
        FileSystemUtils.deleteRecursively(Paths.get(this.outputLocationS).toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(Paths.get(this.rootLocationS));
            Files.createDirectories(Paths.get(this.outputLocationS));
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
