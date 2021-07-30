package com.quickguru.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Entity
public class Answer {
	
	public Answer(Question question, AnswerFile aFile, User user) {
		this.question = question;
		this.file = aFile;
		this.createdBy = user;
		this.createdOn = new Timestamp(System.currentTimeMillis());
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", nullable = false)
	private AnswerFile file;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
	private Question question;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
	private User createdBy;
	
	private Timestamp createdOn;
}
