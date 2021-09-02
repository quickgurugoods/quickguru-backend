package com.quickguru.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quickguru.exception.RecordNotFoundException;
import com.quickguru.model.Question;
import com.quickguru.model.User;
import com.quickguru.repository.UserRepository;
import com.quickguru.service.QuestionService;

@RestController
@RequestMapping("/home")
public class HomeController {
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/status")
	public String appRunning() {
		return "QUICKGURU_HEARTBEAT_SUCCESS";
	}
	
	@GetMapping(value = "/questions") 
	public ResponseEntity<List<Question>> getUserTaggedQuestions(@RequestHeader("email") String email)
	{
		List<Question> questions = null;
		User user = userRepository.findByEmail(email);
		if(user == null) {
			throw new RecordNotFoundException("**No user found at getUserTaggedQuestions: " + email);
		} else if(!user.getTags().isEmpty()) {
			questions = questionService.getQuestionsByUserTag(user);
		}
	    return new ResponseEntity<>(questions, HttpStatus.OK);
	}
	
	@GetMapping(value = "/my-questions") 
	public ResponseEntity<List<Question>> getUserSubmittedQuestions(@RequestHeader("email") String email)
	{
		List<Question> questions = null;
		User user = userRepository.findByEmail(email);
		if(user == null) {
			throw new RecordNotFoundException("**No user found at getUserSubmittedQuestions: " + email);
		} else {
			questions = questionService.getQuestionsSubmittedByUser(user);
		}
	    return new ResponseEntity<>(questions, HttpStatus.OK);
	}
}
