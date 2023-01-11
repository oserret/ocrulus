//package com.expert.ai.ocrulus;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.util.Arrays;
//import java.util.List;
//
//import com.expert.ai.ocrulus.bean.ConversionRequestBean;
//import com.expert.ai.ocrulus.controller.ConversionRequestController;
//import com.expert.ai.ocrulus.service.ConversionRequestService;
//import org.hamcrest.Matchers;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//
//
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(ConversionRequestController.class)
//public class StandaloneConversionRequestControllerTests {
//
//    @MockBean
//    ConversionRequestService conversionRequestService;
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @Test
//    public void testfindAll() throws Exception {
//        ConversionRequestBean conversionRequestBean = new ConversionRequestBean();
//        List<ConversionRequestBean> conversionRequestBeans = Arrays.asList(conversionRequestBean);
//
//        Mockito.when(conversionRequestService.findAll()).thenReturn(conversionRequestBeans);
//
//        mockMvc.perform(get("/employee"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", Matchers.hasSize(1)))
//                .andExpect(jsonPath("$[0].firstName", Matchers.is("Lokesh")));
//    }
//
//}