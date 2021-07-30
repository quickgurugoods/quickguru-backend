package com.quickguru.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.quickguru.exception.QuickGuruException;
import com.quickguru.exception.RecordNotFoundException;
import com.quickguru.model.Answer;
import com.quickguru.model.AnswerFile;
import com.quickguru.model.Question;
import com.quickguru.model.User;
import com.quickguru.model.User.Role;
import com.quickguru.repository.AnswerRepository;
import com.quickguru.repository.QuestionRepository;
import com.quickguru.repository.UserRepository;
import com.quickguru.service.QuestionService;

@RestController
@RequestMapping("/expert")
public class ExpertController {
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping(value = "/my-answers") 
	public ResponseEntity<List<Question>> getAnsweredQuestions(@RequestHeader("email") String email) throws QuickGuruException
	{
		List<Question> questions = null;
		User user = userRepository.findByEmail(email);
		if(user == null) {
			throw new RecordNotFoundException("**No user found at getAnsweredQuestions: " + email);
		} else if(!Role.EXPERT.equals(user.getRole())) {
			throw new QuickGuruException("**Role mismatch at getAnsweredQuestions: " + email);
		} else {
			questions = questionService.getExpertQuestions(user);
		}
	    return new ResponseEntity<>(questions, HttpStatus.OK);
	}
	
	@PostMapping(value="/answer/{question-id}")
	public ResponseEntity<HttpStatus> expertAnswerQuestion(@RequestPart("file") MultipartFile file, @PathVariable("question-id") long questionId,
							 @RequestHeader("email") String email) throws QuickGuruException {
		try {
			User user = userRepository.findByEmail(email);
			AnswerFile aFile = questionService.storeAnswerFile(file);
			
			Question question = null;
			Optional<Question> questions = questionRepository.findById(questionId);
			if(questions.isPresent()) {
				question = questions.get();
			} else {
				throw new RecordNotFoundException("**No question found at expertAnswerQuestion: " + questionId);
			}
			Answer answer = new Answer(question, aFile, user);
			answerRepository.save(answer);
			
			question.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
			questionRepository.save(question);
		} catch (Exception e) {
			throw new QuickGuruException("**expertAnswerQuestion Exception**"+ e.getMessage());
		}
	    return ResponseEntity.ok(HttpStatus.OK);
	}
}
