package com.quickguru.dto;

import lombok.Data;

@Data
public class FeedbackDTO {

	private String comment;
	private int star;
	private long answerId;
}
