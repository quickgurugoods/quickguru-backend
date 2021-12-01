package com.quickguru.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.quickguru.model.Answer;
import com.quickguru.model.User;
import com.quickguru.model.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

	Vote findByAnswerAndVotedBy(Answer answer, User user);
	
	@Query("SELECT SUM(v.up) - SUM(v.down) FROM Vote v where v.answer=:answer")
	int calculateVoteForAnswer(Answer answer);

}