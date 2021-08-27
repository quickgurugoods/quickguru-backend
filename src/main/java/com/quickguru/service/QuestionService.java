package com.quickguru.service;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.quickguru.exception.QuickGuruException;
import com.quickguru.model.AnswerFile;
import com.quickguru.model.Question;
import com.quickguru.model.Question.QStatus;
import com.quickguru.model.QuestionFile;
import com.quickguru.model.Tag;
import com.quickguru.model.User;
import com.quickguru.repository.AnswerFileRepository;
import com.quickguru.repository.QuestionFileRepository;
import com.quickguru.repository.QuestionRepository;

@Service
public class QuestionService {

	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private QuestionFileRepository questionFileRepository;
	
	@Autowired
	private AnswerFileRepository answerFileRepository;
	
	public List<Question> getQuestionsByUserTag(Set<Tag> tags) {
		Set<QStatus> status = EnumSet.of(QStatus.APPROVED, QStatus.ANSWERED);
		return questionRepository.findAllByStatusInAndTagIn(status, tags);
	}

	public List<Question> getQuestionsSubmittedByUser(User user) {
		return questionRepository.findAllByCreatedBy(user);
	}

	public QuestionFile storeQuestionFile(MultipartFile file) throws QuickGuruException {
		QuestionFile qFile = null;
		if(!file.isEmpty()) {
			try {
				qFile = new QuestionFile(file);
				questionFileRepository.save(qFile);
			} catch (Exception e) {
				throw new QuickGuruException("**storeQuestionFile Exception**"+ e.getMessage());
			}
		}
		
		return qFile;
	}

	public AnswerFile storeAnswerFile(MultipartFile file) throws QuickGuruException {
		AnswerFile aFile = null;
		if(!file.isEmpty()) {
			try {
				aFile = new AnswerFile(file);
				answerFileRepository.save(aFile);
			} catch (Exception e) {
				throw new QuickGuruException("**storeAnswerFile Exception**"+ e.getMessage());
			}
		}
		
		return aFile;
	}
	
	public List<Question> getExpertQuestions(User user) {
		List<Question> questions = questionRepository.fetchExpertAnsweredQuestions(user);
		if(!user.getTags().isEmpty()) {
			questions.addAll(questionRepository.findAllByStatusAndTagIn(QStatus.APPROVED, user.getTags()));
		}
		if(!questions.isEmpty()) {
			questions.sort(Comparator.comparing(Question::getUpdatedOn).reversed()); 
		}
		return questions;
	}

}
