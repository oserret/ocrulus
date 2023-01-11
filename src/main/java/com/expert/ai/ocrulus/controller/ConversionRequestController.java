package com.expert.ai.ocrulus.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.expert.ai.ocrulus.bean.ConversionRequestBean;
import com.expert.ai.ocrulus.bean.Message;
import com.expert.ai.ocrulus.bean.UpdateConversionRequestBean;
import com.expert.ai.ocrulus.config.JwtTokenUtil;
import com.expert.ai.ocrulus.service.ConversionRequestService;
import com.expert.ai.ocrulus.utils.Globals;
import com.expert.ai.ocrulus.utils.Utilities;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.Logger;
import io.jsonwebtoken.ExpiredJwtException;

@RestController
@CrossOrigin()
@RequestMapping(path = "/api/conversion")
public class ConversionRequestController
{

	ObjectMapper mapper = new ObjectMapper();
	Logger logger = (Logger) LoggerFactory.getLogger(ConversionRequestController.class);

	@Autowired
	private ConversionRequestService conversionRequestService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	/**
	 * Post request to create a new conversion request into the system.
	 *
	 * @param conversionRequestBean
	 * @return savedConversionRequest
	 */
	@PostMapping("/addConversionRequest")
	public @ResponseBody Object saveNewConversionRequest(final @RequestBody ConversionRequestBean conversionRequestBean, HttpServletRequest request) throws JsonProcessingException
	{
		logger.info("Call: /api/conversion/addConversionRequest SERVICE POST");

		logger.info("Retrieving the token from the request");
		final String requestTokenHeader = request.getHeader("Authorization");
		logger.info("Retrieving the user name from the token");
		conversionRequestBean.setUserName(jwtTokenUtil.extractUserFromToken(requestTokenHeader));

		logger.info("Saving the conversion request for the user: " + conversionRequestBean.getUserName());
		ConversionRequestBean savedConversionRequest = conversionRequestService.saveConversionRequest(conversionRequestBean);
		logger.info("RETURN: /api/conversion/addConversionRequest SERVICE POST");
		
		if (savedConversionRequest != null)
		{
			return savedConversionRequest;
		}
		else
		{
			logger.error(Globals.REQUEST_INSERT_ERROR);
			return Utilities.toJson(new Message(Globals.REQUEST_INSERT_ERROR, Globals.STATUS_KO));
		}
	}

	/**
	 * Get request to retrieve all the conversion requests present into the Database
	 *
	 * @return List<ConversionRequestData>
	 */
	@GetMapping(path = "/all")
	public @ResponseBody Iterable<ConversionRequestBean> getAllRequests()
	{
		logger.info("Call: /api/conversion/all SERVICE");
		return conversionRequestService.findAll();
	}

	/**
     * Get request to retrieve all the conversion requests of a User
     *
     * @return List<ConversionRequestData>
     * @throws JsonProcessingException 
     */
    @GetMapping(path = "/getAllRequestsByUsername")
    public @ResponseBody
    Object getAllRequestsByUsername(HttpServletRequest request, @RequestParam(name="status", required = false) String status) throws JsonProcessingException {
        
    	logger.info("Call: /api/conversion/getAllRequestsByUsername SERVICE");
        
    	String requestTokenHeader = request.getHeader("Authorization");
        String userName = jwtTokenUtil.extractUserFromToken(requestTokenHeader);
        
        if(status == null || status.equals("")) {
        	return conversionRequestService.findAllByUserName(userName);
        }
        else if(status.equals(Globals.CNV_STATUS_PROCESSED) || status.equals(Globals.CNV_STATUS_NOT_PROCESSED)) {
        	return conversionRequestService.findAllByUserNameAndStatus(userName, status);
        } else {
        	return Utilities.toJson(new Message("Status not recognized", Globals.STATUS_KO_BAD_REQUEST));
        }

    }

	/**
	 * Get request to retrieve 1 not processed conversion present into the Database
	 *
	 * @return List<ConversionRequestData>
	 */
	@GetMapping(path = "/getNotProcessedRequest")
	public @ResponseBody ConversionRequestBean getNotProcessedRequest()
	{
		logger.info("Call: /api/conversion/getNotProcessedRequest SERVICE");
		return conversionRequestService.findNotProcessed();
	}

	/**
	 * Get request to retrieve 1 not processed conversion for User
	 *
	 * @return List<ConversionRequestData>
	 */
	@GetMapping(path = "/getNotProcessedRequestUser")
	public @ResponseBody ConversionRequestBean getNotProcessedRequestUser(HttpServletRequest request)
	{

		logger.info("Call: /api/conversion/getNotProcessedRequestUser SERVICE");

		String requestTokenHeader = request.getHeader("Authorization");
		String userName = jwtTokenUtil.extractUserFromToken(requestTokenHeader);

		return conversionRequestService.findNotProcessedByUserName(userName);
	}

	/**
	 * Get request to retrieve 1 not processed conversion for User
	 *
	 * @return List<ConversionRequestData>
	 */
	@GetMapping(path = "/getNotProcessedRequestStatus")
	public @ResponseBody ConversionRequestBean getNotProcessedRequestStatus(HttpServletRequest request,  @RequestParam(name="status", required = true) String status)
	{

		logger.info("Call: /api/conversion/getNotProcessedRequestStatus SERVICE");
		return conversionRequestService.findNotProcessedByStatus(status);
	}


	/**
	 * Post request to update a processed request
	 *
	 * @return List<ConversionRequestData>
	 */
	@PostMapping("/updateConversionRequest")
	public @ResponseBody Object updateConversionRequest(final @RequestBody UpdateConversionRequestBean updateConversionRequestBean) throws JsonProcessingException
	{
		logger.info("Call: /api/conversion/updateConversionRequest SERVICE POST");

		ConversionRequestBean savedConversionRequest = conversionRequestService.updateConversionRequest(updateConversionRequestBean);
		logger.info("RETURN: /api/conversion/addConversionRequest SERVICE POST");
		if (savedConversionRequest != null)
		{
			return savedConversionRequest;
		}
		else
		{
			logger.error(Globals.REQUEST_INSERT_ERROR);
			return Utilities.toJson(new Message(Globals.REQUEST_INSERT_ERROR, Globals.STATUS_KO));
		}
	}

	/**
	 * Post request to delete a processed request
	 *
	 * @return String ok or not ok
	 */
	@DeleteMapping("/deleteConversionRequest/{id}")
	public @ResponseBody Object deleteConversionRequest(HttpServletRequest request, @PathVariable("id") String id) throws JsonProcessingException
	{
		logger.info("Call: /api/conversion/deleteConversionRequest SERVICE");

		String requestTokenHeader = request.getHeader("Authorization");
		String userName = jwtTokenUtil.extractUserFromToken(requestTokenHeader);

		ConversionRequestBean savedConversionRequest = conversionRequestService.findByUserNameAndId(userName, Long.parseLong(id));

		if (savedConversionRequest != null)
		{
			conversionRequestService.delete(Long.parseLong(id));
			return Utilities.toJson(new Message("Ok", Globals.STATUS_OK));
		}
		else
		{
			logger.error("Unable to delete request with id: " + id + " it is not part of the users ones");
			return Utilities.toJson(new Message("Unable to delete request with id: " + id + " it is not part of the users ones", Globals.STATUS_KO));
		}

	}
	
	@DeleteMapping("/deleteSelectedConversionRequest")
	public @ResponseBody Object deleteSelectedConversionRequest(HttpServletRequest request, @RequestBody List<Long> ids) throws JsonProcessingException
	{
		logger.info("Call: /api/conversion/deleteSelectedConversionRequest SERVICE");

		String requestTokenHeader = request.getHeader("Authorization");
		String userName = jwtTokenUtil.extractUserFromToken(requestTokenHeader);
		
		conversionRequestService.deleteAll(ids);
		
		return Utilities.toJson(new Message("Ok", Globals.STATUS_OK));

//		ConversionRequestBean savedConversionRequest = conversionRequestService.findByUserNameAndId(userName, Long.parseLong(id));
//
//		if (savedConversionRequest != null)
//		{
//			conversionRequestService.delete(Long.parseLong(id));
//			return Utilities.toJson(new Message("Ok", Globals.STATUS_OK));
//		}
//		else
//		{
//			logger.error("Unable to delete request with id: " + id + " it is not part of the users ones");
//			return Utilities.toJson(new Message("Unable to delete request with id: " + id + " it is not part of the users ones", Globals.STATUS_KO));
//		}

	}

}
