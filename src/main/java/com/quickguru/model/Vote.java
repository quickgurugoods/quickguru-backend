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
public class Vote {
	
	public Vote() {
		// Default constructor
	}

	public Vote(User user, Answer answer, String type) {
		this.createdOn =  new Timestamp(System.currentTimeMillis());
		this.updatedOn =  new Timestamp(System.currentTimeMillis());
		this.votedBy = user;
		this.answer = answer;
		if("up".equalsIgnoreCase(type)) {
			this.up = 1;
			this.down = 0;
		} else {
			this.up = 0;
			this.down = 1;
		}
	}

	public Vote(String type, Vote vote) {
		this.id = vote.id;
		if("up".equalsIgnoreCase(type)) {
			this.up = 1;
			this.down = 0;
		} else {
			this.up = 0;
			this.down = 1;
		}
		
		this.createdOn =  vote.createdOn;
		this.updatedOn =  new Timestamp(System.currentTimeMillis());
		this.votedBy = vote.votedBy;
		this.answer = vote.answer;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int up;
	private int down;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
	private User votedBy;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id", nullable = false)
	private Answer answer;
	
	private Timestamp createdOn;
	private Timestamp updatedOn;
}

