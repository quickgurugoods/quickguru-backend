package com.quickguru.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickguru.model.QuestionFile;

@Repository
public interface QuestionFileRepository extends JpaRepository<QuestionFile, Long> {

}