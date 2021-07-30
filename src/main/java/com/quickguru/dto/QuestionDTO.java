package com.quickguru.dto;

import com.quickguru.model.Language;
import com.quickguru.model.Tag;

import lombok.Data;

@Data
public class QuestionDTO {
	
	
	private String title;
	private Tag tag;
	private Language language;
}

