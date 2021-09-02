package com.quickguru.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.quickguru.dto.QuestionDTO;
import com.quickguru.exception.QuickGuruException;
import com.quickguru.exception.RecordNotFoundException;
import com.quickguru.model.Answer;
import com.quickguru.model.AnswerFile;
import com.quickguru.model.Question;
import com.quickguru.model.QuestionFile;
import com.quickguru.model.User;
import com.quickguru.repository.AnswerFileRepository;
import com.quickguru.repository.AnswerRepository;
import com.quickguru.repository.QuestionFileRepository;
import com.quickguru.repository.QuestionRepository;
import com.quickguru.repository.UserRepository;
import com.quickguru.service.QuestionService;

@RestController
@CrossOrigin
@RequestMapping("/question")
public class QuestionController {

	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@Autowired
	private QuestionFileRepository questionFileRepository;
	
	@Autowired
	private AnswerFileRepository answerFileRepository;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping(value = "/{id}") 
	public ResponseEntity<Question> getQuestionById(@PathVariable("id") long id)
	{
		Question question = new Question();
		Optional<Question> questions = questionRepository.findById(id);
		if(questions.isPresent()) {
			question = questions.get();
		}
	    return new ResponseEntity<>(question, HttpStatus.OK);
	}
	
	@GetMapping(value = "/answer/{id}") 
	public ResponseEntity<Answer> getAnswerByQuestionId(@PathVariable("id") long id)
	{
		Question question = new Question(id);
		Answer answer = answerRepository.findByQuestion(question);
		if(answer == null) {
			throw new RecordNotFoundException("**No answer found at getAnswerByQuestionId: " + id);
		}
	    return new ResponseEntity<>(answer, HttpStatus.OK);
	}
	
	@PostMapping(value="/ask")
	public ResponseEntity<HttpStatus> userAskQuestion( @RequestPart("file") MultipartFile file,
			@RequestPart("question") QuestionDTO questionDTO, @RequestHeader("email") String email) throws QuickGuruException {
		try {
			User user = userRepository.findByEmail(email);
			QuestionFile qFile = questionService.storeQuestionFile(file);
			Question question = new Question(questionDTO, qFile, user);
			questionRepository.save(question);
		} catch (Exception e) {
			throw new QuickGuruException("**userAskQuestion Exception**"+ e.getMessage());
		}
	    return ResponseEntity.ok(HttpStatus.OK);
	}
	

	@GetMapping("/q-file/{id}")
    public ResponseEntity<Resource> downloadQuestionFile(@PathVariable long id) {
		QuestionFile qFile = new QuestionFile();
		Optional<QuestionFile> qFiles = questionFileRepository.findById(id);
		if(qFiles.isPresent()) {
			qFile = qFiles.get();
		}
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(qFile.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + qFile.getName() + "\"")
                .body(new ByteArrayResource(qFile.getData()));
    }
	
	
	@GetMapping("/a-file/{id}")
    public ResponseEntity<Resource> downloadAnswerFile(@PathVariable long id) {
		AnswerFile aFile = new AnswerFile();
		Optional<AnswerFile> aFiles = answerFileRepository.findById(id);
		if(aFiles.isPresent()) {
			aFile = aFiles.get();
		}
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(aFile.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + aFile.getName() + "\"")
                .body(new ByteArrayResource(aFile.getData()));
    }
}
