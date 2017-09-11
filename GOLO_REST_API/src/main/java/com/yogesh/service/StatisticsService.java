package com.yogesh.service;

import static java.text.MessageFormat.format;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import com.yogesh.dto.ServerStatisticsDTO;
import com.yogesh.exception.OverviewNotAvailableException;
import com.yogesh.model.ThreadSafeDatabase;

/**
 * @author Yogesh Thakare
 */
@Service
public class StatisticsService {
	
	
	 public List<ServerStatisticsDTO> getOverview(String hostname) throws OverviewNotAvailableException
	 {
		 	Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        ThreadSafeDatabase TSD = ThreadSafeDatabase.getHandle();
	        if (!TSD.getCACHE().containsKey(hostname))
	    		throw new OverviewNotAvailableException(format("Could not collect statistics for {0} server", hostname));
	        
            List<ServerStatisticsDTO> serverStatisticsDTOList = new ArrayList<ServerStatisticsDTO>();
	        
	        TSD.getCACHE().get(hostname).forEach((time,state)->
	        {	
	        	ServerStatisticsDTO serverStatisticsDTO = new ServerStatisticsDTO();
	        	serverStatisticsDTO.setStartedSince(formatter.format(new Date()));
	        	serverStatisticsDTO.setTime(time);
	            serverStatisticsDTO.setState(state);
	            serverStatisticsDTOList.add(serverStatisticsDTO);
	        	
	        });

	        return serverStatisticsDTOList;
	    }

}
