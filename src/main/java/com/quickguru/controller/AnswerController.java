package com.quickguru.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quickguru.dto.FeedbackDTO;
import com.quickguru.dto.VoteDTO;
import com.quickguru.exception.QuickGuruException;
import com.quickguru.exception.RecordNotFoundException;
import com.quickguru.model.Answer;
import com.quickguru.model.Feedback;
import com.quickguru.model.User;
import com.quickguru.model.Vote;
import com.quickguru.repository.FeedbackRepository;
import com.quickguru.repository.UserRepository;
import com.quickguru.repository.VoteRepository;

@RestController
@RequestMapping("/answer")
public class AnswerController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FeedbackRepository feedbackRepository;
	
	@Autowired
	private VoteRepository voteRepository;

	@GetMapping(value = "/feedback/{answer-id}") 
	public ResponseEntity<Feedback> getFeedbackByAnswerId (@PathVariable("answer-id") long answerId)
	{
		Answer answer = new Answer(answerId);
		Feedback feedback = feedbackRepository.findByAnswer(answer);
	    return new ResponseEntity<>(feedback, HttpStatus.OK);
	}
	
	@PostMapping("/feedback")
	public ResponseEntity<HttpStatus> submitFeedback(@RequestBody FeedbackDTO feedbackDTO, @RequestHeader("email") String email) throws QuickGuruException {
		
		try {
			User user = userRepository.findByEmail(email);
			if(user == null) {
				throw new RecordNotFoundException("**No user found for submitFeedback: " + email);
			} else {
				Feedback feedback = new Feedback(feedbackDTO, user);
				feedbackRepository.save(feedback);
			}
		} catch (Exception e) {
			throw new QuickGuruException("**submitFeedback Exception**"+ e.getMessage());
		}
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@GetMapping(value = "/vote/{answer-id}") 
	public ResponseEntity<VoteDTO> getVoteByAnswerId (@PathVariable("answer-id") long answerId, 
			@RequestHeader("email") String email) throws QuickGuruException
	{
		VoteDTO voteDTO = null;
		try {
			User user = userRepository.findByEmail(email);
			if(user == null) {
				throw new RecordNotFoundException("**No user found for getVoteByAnswerId: " + email);
			} else {
				Answer answer = new Answer(answerId);
				Vote vote = voteRepository.findByAnswerAndVotedBy(answer, user);
				voteDTO = new VoteDTO(vote, voteRepository.calculateVoteForAnswer(answer));
			}
		} catch (Exception e) {
			throw new QuickGuruException("**getVoteByAnswerId Exception**"+ e.getMessage());
		}
		
	    return new ResponseEntity<>(voteDTO, HttpStatus.OK);
	}
	
	
	
	@PostMapping("/vote/{vote-type}/{answer-id}")
	public ResponseEntity<HttpStatus> submitVote(@PathVariable("answer-id") long answerId, @PathVariable("vote-type") String type,  
							@RequestHeader("email") String email) throws QuickGuruException {
		Vote vote = null;
		try {
			User user = userRepository.findByEmail(email);
			if(user == null) {
				throw new RecordNotFoundException("**No user found for submitVote: " + email);
			} else {
				Answer answer = new Answer(answerId);
				vote = voteRepository.findByAnswerAndVotedBy(answer, user);
				if(vote == null) {
					vote = new Vote(user, answer, type);
				} else {
					vote = new Vote(type, vote);
				}
				
				voteRepository.save(vote);
			}
		} catch (Exception e) {
			throw new QuickGuruException("**submitVote Exception**"+ e.getMessage());
		}
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
}
