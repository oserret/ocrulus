package com.expert.ai.ocrulus.controller;

import ch.qos.logback.classic.Logger;
import com.expert.ai.ocrulus.bean.ConverterParametersBean;
import com.expert.ai.ocrulus.service.ConverterParametersService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin()
@RequestMapping(path = "/api/configuration")
public class ConverterParametersController {
    Logger logger = (Logger) LoggerFactory.getLogger(ConverterParametersController.class);

    @Autowired
    private ConverterParametersService converterParametersService;

    /**
     * Get request to retrieve the configuration of an engine
     *
     * @return List<ConverterParametersEntity>
     * @throws JsonProcessingException
     */
    @GetMapping(path = "/getConverterParametersByEngine")
    public @ResponseBody
    Object getConverterParametersByEngine(@RequestParam(name = "engine") String engine) {
        logger.info("Call: /api/conversion/getConverterParametersByEngine SERVICE");
        return converterParametersService.findByEngine(engine);
    }

    /**
     * Post request to update a processed request
     *
     * @return List<ConversionRequestData>
     */
    @PostMapping("/updateConverterParameters")
    public @ResponseBody
    Object updateConverterParameters(@RequestBody ConverterParametersBean converterParametersBean) {
        logger.info("Call: /api/conversion/updateConverterParameters SERVICE");
        return converterParametersService.updateConverterParameters(converterParametersBean);
    }
}
