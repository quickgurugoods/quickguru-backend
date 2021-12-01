package com.quickguru.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.quickguru.dto.FeedbackDTO;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Entity
public class Feedback {
	
	public Feedback() {
		// Default constructor
	}

	public Feedback(FeedbackDTO feedbackDTO, User user) {
		this.comment = feedbackDTO.getComment();
		this.star = feedbackDTO.getStar();
		this.createdBy = user;
		this.createdOn = new Timestamp(System.currentTimeMillis());
		this.answer = new Answer(feedbackDTO.getAnswerId());
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String comment;
	private int star;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
	private User createdBy;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id", nullable = false)
	private Answer answer;
	
	private Timestamp createdOn;
}

