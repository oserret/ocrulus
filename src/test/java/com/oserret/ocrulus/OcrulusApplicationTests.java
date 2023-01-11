package com.oserret.ocrulus;

import com.oserret.ocrulus.controller.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OcrulusApplicationTests {

	@Autowired
	private ConversionRequestController conversionRequestController;

	@Autowired
	private FileUploadController fileUploadController;

	@Autowired
	private JwtAuthenticationController jwtAuthenticationController;

	@Autowired
	private LoggingController loggingController;

	@Autowired
	private UserController userController;

	@Test
	public void contextLoads() {
		assertNotNull(conversionRequestController);
		assertNotNull(fileUploadController);
		assertNotNull(jwtAuthenticationController);
		assertNotNull(loggingController);
		assertNotNull(userController);

	}

}
