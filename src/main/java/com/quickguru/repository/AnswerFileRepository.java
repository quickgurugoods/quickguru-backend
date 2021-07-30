package com.quickguru.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickguru.model.AnswerFile;

@Repository
public interface AnswerFileRepository extends JpaRepository<AnswerFile, Long> {

}