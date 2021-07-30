package com.quickguru.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quickguru.dto.UserDTO;
import com.quickguru.exception.QuickGuruException;
import com.quickguru.exception.RecordNotFoundException;
import com.quickguru.model.User;
import com.quickguru.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;

	@GetMapping(value = "/profile") 
	public ResponseEntity<User> getUserById (@RequestHeader("email") String email)
	{
		User user = userRepository.findByEmail(email);
		if(user == null) {
			throw new RecordNotFoundException("**No user found at getUserById: " + email);
		} else {
			user.getTags().iterator();
			user.getLanguages().iterator();
		}
	    return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<User> loginValidation(@RequestBody UserDTO userDTO) throws QuickGuruException {
		
		try {
			User user = userRepository.findByEmail(userDTO.getUserName());
			if(user == null) {
				throw new RecordNotFoundException("**No user found for loginValidation: " + userDTO);
			} else if(userDTO.getValidator().equals(user.getVerifier())) {
				return new ResponseEntity<>(user, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new User(), HttpStatus.OK);
			}
		} catch (Exception e) {
			throw new QuickGuruException("**loginValidation Exception**"+ e.getMessage());
		}
	}
	
//	@GetMapping(value = "/dashboard") 
//	public ResponseEntity<User> getDashboard(@RequestHeader("email") String email)
//	{
//		try {
//			// Questions by status 
//			// questions by tags
//			// Questions by Languague
//			
//			English 20 -> Languague
//			College 30 -> Tags
//			SUBMITTED 20 -> Status
//			
//			
//		} catch (Exception e) {
//			throw new QuickGuruException("**getDashboard Exception**"+ e.getMessage());
//		}
//	    return new ResponseEntity<>(null, HttpStatus.OK);
//	}
	
}
