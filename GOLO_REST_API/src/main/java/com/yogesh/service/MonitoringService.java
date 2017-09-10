package com.yogesh.service;

import static java.text.MessageFormat.format;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.yogesh.model.ApiAccessibilityResource;
import com.yogesh.model.ThreadSafeDatabase;

@Service
public class MonitoringService {
	
	private static final Logger log = LoggerFactory.getLogger(MonitoringService.class);
	
    public String startMonitoring(String hostname, Long interval) 
    {
        ThreadSafeDatabase threadSafeDatabase = ThreadSafeDatabase.getHandle();
        HashMap<String, ConcurrentHashMap<String,String>> data = new HashMap<>();
            data.put(hostname,new ConcurrentHashMap<>());

            threadSafeDatabase.setCACHE(data);
            try {
            	 monitorServer(hostname,interval, threadSafeDatabase);
            } catch (InterruptedException ex) {
            	 Thread.currentThread().interrupt();
                log.error("Failed to monitor server", ex.getMessage());
            }

        return "monitoring started on "+hostname;
    }
    
    
    public String stopMonitoring(String hostname) {

        ThreadSafeDatabase threadSafeDatabase = ThreadSafeDatabase.getHandle();

        Iterator<HashMap.Entry<String, ConcurrentHashMap<String,String>>> entries = threadSafeDatabase.getCACHE().entrySet().iterator();
        while(entries.hasNext())
        {
        	HashMap.Entry<String, ConcurrentHashMap<String,String>> entry = entries.next();
            if (entry.getKey().equals(hostname)) {
                entries.remove();
            }
        }
        log.info(format("Server  with id {0} is no more monitored!", hostname));
        return "monitoring stopped on "+hostname;
    }

    
    public static void monitorServer(String hostname, Long interval, ThreadSafeDatabase vitrualMonitoringDataStore) throws InterruptedException{
        RestTemplate restTemplate = new RestTemplate();
        Timer timer = new Timer();
        log.info("Probe running on server" + hostname+ "......");
        boolean isRunning = true;
        TimerTask task = new TimerTask() 
        {
        	
        	 @Override
            public void run() {
                if(isRunning) {
                	ConcurrentHashMap<String,String> map = vitrualMonitoringDataStore.getCACHE().get(hostname);
                    if(map!=null) {
                        map.put(new Date().toString(), restTemplate.getForEntity(hostname, ApiAccessibilityResource.class).getBody().getMessage());
                    } else {
                        timer.cancel();
                        timer.purge();
                    }
                }            }
        };

        timer.scheduleAtFixedRate(task, interval, interval);
    }
    

}
