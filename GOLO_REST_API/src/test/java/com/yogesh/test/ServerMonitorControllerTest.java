package com.yogesh.test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;

import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import com.yogesh.controller.ServerMonitorController;
import com.yogesh.dto.ServerStatisticsDTO;
import com.yogesh.dto.ServerStatisticsSetDTO;
import com.yogesh.service.MonitorService;
import com.yogesh.service.StatisticsService;

@RunWith(SpringRunner.class)
@WebMvcTest({ServerMonitorController.class})
@AutoConfigureMockMvc    
public class ServerMonitorControllerTest 
{
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	MonitorService monitorService;

	@MockBean
	StatisticsService statisticsService;
		
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(
				new ServerMonitorController(monitorService, statisticsService)).build();
	}
	
	@Test
	public void testMissingRequiredParam() throws Exception 
	{
		mockMvc.perform(get("/api/monitor/start?")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().reason(containsString("Request method 'GET' not supported")))
				.andExpect(status().isMethodNotAllowed());
	}
	
	@Test
	public void testWrongEventUrl() throws Exception 
	{
		 mockMvc.perform(get("/api/monitor/start?hostname=url")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success").doesNotExist())
				.andExpect(status().isMethodNotAllowed());
	}
	
	@Test
	public void testMonitoringStartForServer() throws Exception {

		when(monitorService.startMonitoring(anyString(), anyLong()))
				.thenReturn("monitoring started on https://api.test.paysafe.com/accountmanagement/monitor");

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/monitor/start")
				.header("hostname", "localhost:8090").accept(MediaType.APPLICATION_JSON)
				.param("hostname", "https://api.test.paysafe.com/accountmanagement/monitor").param("interval", "500");
        ResultActions result = mockMvc.perform(requestBuilder);
        
        Assert.notNull(result);
        verify(monitorService, times(1)).startMonitoring("https://api.test.paysafe.com/accountmanagement/monitor", 500L);
		
	}
	
	@Test
	public void testMonitoringStopForServer() throws Exception 
	{

		when(monitorService.startMonitoring(anyString(), anyLong()))
				.thenReturn("monitoring stopped on https://api.test.paysafe.com/accountmanagement/monitor");

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/monitor/stop")
				.header("hostname", "localhost:8090").accept(MediaType.APPLICATION_JSON)
				.param("hostname", "https://api.test.paysafe.com/accountmanagement/monitor");
        ResultActions result = mockMvc.perform(requestBuilder);
        
        Assert.notNull(result);
        verify(monitorService, times(1)).stopMonitoring("https://api.test.paysafe.com/accountmanagement/monitor");
		
	}
	
	@Test
	public void testGetOverviewFromServer() throws Exception 
	{
		List<ServerStatisticsDTO> overview= new ArrayList<ServerStatisticsDTO>();
		
		when(statisticsService.getOverview(anyString())).thenReturn(overview);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/monitor/overview")
				.header("hostname", "localhost:8090").accept(MediaType.APPLICATION_JSON)
				.param("hostname", "https://api.test.paysafe.com/accountmanagement/monitor");
		ResultActions result = mockMvc.perform(requestBuilder);
        
        Assert.notNull(result);
        verify(statisticsService, times(1)).getOverview("https://api.test.paysafe.com/accountmanagement/monitor");
		
	}

}
