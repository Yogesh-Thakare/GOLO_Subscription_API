package com.yogesh.controller;

import static java.text.MessageFormat.format;

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

import com.yogesh.dto.EmptyResponseDTO;
import com.yogesh.dto.ErrorResponseDTO;
import com.yogesh.dto.ErrorResponseDTO.ErrorCode;
import com.yogesh.dto.ResponseDTO;
import com.yogesh.dto.ServerStatisticsSet;
import com.yogesh.dto.SuccessResponseDTO;
import com.yogesh.exception.InvalidCommadException;
import com.yogesh.service.MonitorService;
import com.yogesh.service.StatisticsService;

/**
 * @author Yogesh Thakare
 */
@RestController
@RequestMapping("/api/monitor")
public class ServerMonitorController 
{
	private final MonitorService monitoringService;
	private final StatisticsService statisticsService;
	
	private static final Logger log = LoggerFactory.getLogger(ServerMonitorController.class);

	@Autowired
	public ServerMonitorController(MonitorService monitoringService, StatisticsService statisticsService) 
	{
		this.monitoringService=monitoringService;
		this.statisticsService=statisticsService;
	}
	
	@RequestMapping(value = "/{command}", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO> performAction(@PathVariable("command") String command,
			@RequestParam(value = "hostname", required = true) String hostname,
			@RequestParam(value = "interval", required = false) Long interval) 
	{
		 String response="";
		 try 
		 {
			 log.info(format("Action triggered on server  with id {0}", hostname));
			 if(command.equals("start"))
				 response=monitoringService.startMonitoring(hostname,interval);
			 else if(command.equals("stop"))
				 response=monitoringService.stopMonitoring(hostname);
			 else throw new InvalidCommadException("Command is not valid");

			return new ResponseEntity<>(new SuccessResponseDTO(response), HttpStatus.OK);
		 }
		 catch (InvalidCommadException ex) 
		 {
			log.error(ex.getMessage());
			return new ResponseEntity<>(new ErrorResponseDTO(ErrorCode.INVALID_COMMAND, ex.getMessage()), HttpStatus.OK);
		 }
		 catch (Exception ex) 
		 {
			log.error(ex.getMessage());
			return new ResponseEntity<>(new ErrorResponseDTO(ErrorCode.UNKNOWN_ERROR, ex.getMessage()), HttpStatus.OK);
		 }
	 }
	 
	 @RequestMapping(value="/overview",method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	 public ResponseEntity<ServerStatisticsSet> getStatistics(  @RequestParam(value = "hostname", required=true) String hostname ) 
	 {
		try
		{
			ServerStatisticsSet statistics = new ServerStatisticsSet();
			statistics.setOverview(statisticsService.getOverview(hostname));
			return new ResponseEntity<ServerStatisticsSet>(statistics, HttpStatus.OK);
		}
		catch (Exception ex) 
		{
			log.error(ex.getMessage());
			return new ResponseEntity<>(new EmptyResponseDTO(com.yogesh.dto.EmptyResponseDTO.ErrorCode.STATS_NOT_AVAILABLE, ex.getMessage()), HttpStatus.OK);
		}
	 }
}