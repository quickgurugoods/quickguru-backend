package com.quickguru.dto;

import java.util.Map;

import lombok.Data;

@Data
public class DashboardDTO {
	
	public DashboardDTO() {
		// Constructor
	}
	
	private long noOfQuestions;
	private long noOfAnswers;
	private long noOfUnanswered;
	private long noOfRejectedQuestions;
	private Map<String, Long> countOfQuestionsByStatus;
	private Map<String, Long> countOfQuestionsByTag;
	private Map<String, Long> countOfQuestionsByLanguage;
}

