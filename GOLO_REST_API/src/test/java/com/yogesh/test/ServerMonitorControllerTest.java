package com.yogesh.test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.yogesh.controller.ServerMonitorController;
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
}
