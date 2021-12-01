package com.quickguru.dto;

import com.quickguru.model.Vote;

import lombok.Data;

@Data
public class VoteDTO {

	public VoteDTO() { }
	
	public VoteDTO(Vote vote, int noOfVotes) {
		if(vote == null) {
			this.votedFor = "NA";
		} else {
			this.votedFor = vote.getUp() == 1 ? "UP" : "DOWN"; 
		}
		this.noOfVotes = noOfVotes;
	}
	
	private String votedFor;
	private int noOfVotes;
}
