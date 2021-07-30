package com.quickguru.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickguru.model.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {


}