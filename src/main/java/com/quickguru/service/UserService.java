package com.quickguru.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickguru.repository.QuestionRepository;

@Service
public class UserService {

	@Autowired
	private QuestionRepository questionRepository;
	
	
}
