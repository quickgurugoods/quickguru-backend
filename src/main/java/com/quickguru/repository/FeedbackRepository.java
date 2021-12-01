package com.quickguru.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickguru.model.Answer;
import com.quickguru.model.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

	Feedback findByAnswer(Answer answer);

}