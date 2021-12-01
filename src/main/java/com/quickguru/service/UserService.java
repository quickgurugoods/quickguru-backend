package com.quickguru.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.quickguru.dto.DashboardDTO;
import com.quickguru.model.Question.QStatus;

@Service
public class UserService {

	public Map<String, Long> objToMapForDashboardStatus(List<Object[]> byStatus) {
		Map<String, Long> byGroup = new HashMap<>();
		for (Object[] obj : byStatus){
			byGroup.put(((QStatus)obj[0]).getCode(), (Long)obj[1]);
		}
		return byGroup;
	}
	
	public Map<String, Long> objToMapForDashboard(List<Object[]> byTag) {
		Map<String, Long> byGroup = new HashMap<>();
		for (Object[] obj : byTag){
			byGroup.put((String)obj[0], (Long)obj[1]);
		}
		return byGroup;
	}

	public DashboardDTO fillQuestionStatusFields(Map<String, Long> byStatusMap, DashboardDTO dashboard) {

		long approvedQns = byStatusMap.containsKey(QStatus.APPROVED.getCode()) ? byStatusMap.get(QStatus.APPROVED.getCode()) : 0L;
		long submittedQns = byStatusMap.containsKey(QStatus.SUBMITTED.getCode()) ? byStatusMap.get(QStatus.SUBMITTED.getCode()) : 0L;
		dashboard.setNoOfUnanswered(approvedQns + submittedQns);
		
		dashboard.setNoOfRejectedQuestions(byStatusMap.containsKey(QStatus.REJECTED.getCode()) ? byStatusMap.get(QStatus.REJECTED.getCode()) : 0L);
		
		return dashboard;
	}
	
	
}
