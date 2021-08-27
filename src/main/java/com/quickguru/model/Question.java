package com.quickguru.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.quickguru.dto.QuestionDTO;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Entity
public class Question {
	
	public Question(QuestionDTO questionDTO, QuestionFile qFile, User user) {
		this.title = questionDTO.getTitle();
		this.file = qFile;
		this.status = QStatus.SUBMITTED;
		this.tag = questionDTO.getTag();
		this.language = questionDTO.getLanguage();
		this.createdBy = user;
		
		this.createdOn = new Timestamp(System.currentTimeMillis());
		this.updatedOn = new Timestamp(System.currentTimeMillis());
	}

	public Question() {
	}

	public Question(long id) {
		this.id = id;
	}

	public enum QStatus {
		SUBMITTED("SUBMITTED"), APPROVED("APPROVED"), REJECTED("REJECTED"), ANSWERED("ANSWERED");
		
		// ADD ANSWERED
	   
	    private final String code;

	    private QStatus(String code) {
	        this.code = code;
	    }

	    public String getCode() {
	        return code;
	    }
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
	private QuestionFile file;
	
	@Enumerated(EnumType.STRING)
	private QStatus status;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
	private Tag tag;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id")
	private Language language;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
	private User createdBy;
	
	private Timestamp createdOn;
	private Timestamp updatedOn;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by")
	private User approvedBy;
	
	private Timestamp approvedOn;
	
}

