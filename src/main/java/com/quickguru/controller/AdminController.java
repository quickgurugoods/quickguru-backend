package com.quickguru.controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quickguru.exception.QuickGuruException;
import com.quickguru.exception.RecordNotFoundException;
import com.quickguru.model.Question;
import com.quickguru.model.Question.QStatus;
import com.quickguru.model.User;
import com.quickguru.model.User.Role;
import com.quickguru.repository.QuestionRepository;
import com.quickguru.repository.UserRepository;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping(value = "/questions") 
	public ResponseEntity<List<Question>> getAllSubmittedQuestions(@RequestHeader("email") String email) throws QuickGuruException
	{
		List<Question> questions = null;
		User user = userRepository.findByEmail(email);
		if(user == null) {
			throw new RecordNotFoundException("**No user found at getAllSubmittedQuestions: " + email);
		} else if(!Role.ADMIN.equals(user.getRole())) {
			throw new QuickGuruException("**Role mismatch at getAllSubmittedQuestions: " + email);
		} else {
			questions = questionRepository.findAllByStatus(QStatus.SUBMITTED);
		}
	    return new ResponseEntity<>(questions, HttpStatus.OK);
	}
	
	@PostMapping("/question/{quesion-id}/{action}")
	@Transactional
	public ResponseEntity<HttpStatus> actionToQuestionByAdmin(@PathVariable("quesion-id") long questionId, @PathVariable("action") String action, 
					@RequestHeader("email") String email)  throws QuickGuruException {
		try {
			User user = userRepository.findByEmail(email);
			if(user == null) {
				throw new RecordNotFoundException("Enable: No user found for actionToQuestionByAdmin Question: " + questionId);
			} else if(!Role.ADMIN.equals(user.getRole())) {
				throw new QuickGuruException("**Role mismatch at actionToQuestionByAdmin: " + email);
			} else {
				QStatus qStatus = "APPROVED".equalsIgnoreCase(action) ? QStatus.APPROVED : QStatus.REJECTED;
				questionRepository.adminActionToQuestion(questionId, user, new Timestamp(System.currentTimeMillis()), qStatus);
			}
		} catch (Exception e) {
			throw new QuickGuruException("**actionToQuestionByAdmin Exception**"+ e.getMessage());
		}
	    return ResponseEntity.ok(HttpStatus.OK);
	}
}
