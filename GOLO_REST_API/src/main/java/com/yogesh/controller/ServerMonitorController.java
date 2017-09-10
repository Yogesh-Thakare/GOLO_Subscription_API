package com.yogesh.controller;

import static java.text.MessageFormat.format;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yogesh.dto.ErrorResponse;
import com.yogesh.dto.ErrorResponse.ErrorCode;
import com.yogesh.dto.Response;
import com.yogesh.dto.SuccessResponse;
import com.yogesh.service.MonitoringService;

/**
 * @author Yogesh Thakare
 */
@RestController
@RequestMapping("/api/monitor")
public class ServerMonitorController 
{
	private final MonitoringService monitoringService;
	
	private static final Logger log = LoggerFactory.getLogger(ServerMonitorController.class);


	@Autowired
	public ServerMonitorController(MonitoringService monitoringService) 
	{
		this.monitoringService=monitoringService;
	}
	
	 @RequestMapping(value = "/{command}", method = RequestMethod.POST,  produces = {MediaType.APPLICATION_JSON_VALUE})
	 public ResponseEntity<Response> performAction(  @PathVariable ("command") String command,
	            @RequestParam(value = "hostname", required=true) String hostname ,
	            @RequestParam(value = "interval", required=false) Long interval) 
	 {
		 String response="";
		 try 
		 {
			 log.info(format("Action triggered on server  with id {0}", hostname));
			 if(command.equals("start"))
				 response=monitoringService.startMonitoring(hostname,interval);
			 else if(command.equals("stop"))
				 response=monitoringService.stopMonitoring(hostname);

			return new ResponseEntity<>(new SuccessResponse(response), HttpStatus.OK);
		} 
		catch (Exception ex) 
		{
			log.error(ex.getMessage());
			return new ResponseEntity<>(new ErrorResponse(ErrorCode.UNKNOWN_ERROR, ex.getMessage()), HttpStatus.OK);
		}
	  }
}