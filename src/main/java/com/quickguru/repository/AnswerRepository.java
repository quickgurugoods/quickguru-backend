package com.quickguru.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickguru.model.Answer;
import com.quickguru.model.Question;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

	Answer findByQuestion(Question question);


}