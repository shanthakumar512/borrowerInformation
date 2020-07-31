/**
 * 
 */
package com.rabobank.loanuserinformation.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rabobank.loanuserinformation.exceptions.LoanUserNotFoundException;
import com.rabobank.loanuserinformation.exceptions.UserDetailsAlreadyExistForEmailIDException;
import com.rabobank.loanuserinformation.model.LoanUser;
import com.rabobank.loanuserinformation.request.LoanUserRequest;
import com.rabobank.loanuserinformation.services.LoanUsersService;

/**
 * @author Shanthakumar
 *
 */

@RestController
@RequestMapping("/loanUser")
public class LoanUserInformationController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoanUserInformationController.class);
	
	@Autowired
	LoanUsersService  loanUsersService;
	
	@PostMapping("/addLoanUser")
	public ResponseEntity<List<LoanUser>> addLoanUser(@Valid @RequestBody LoanUserRequest loanUserRequest) throws UserDetailsAlreadyExistForEmailIDException{		
		
		logger.debug("Add user information request with User Name {} User Email {} ", loanUserRequest.getUserFirstname(),loanUserRequest.getUserEmail());
	
		loanUsersService.addLoanUser(loanUserRequest);
		logger.info("Loan user information Added successfullly"); 
		List<LoanUser> loanUsers= loanUsersService.findAllUsers();
		logger.debug("Total users added {}", loanUsers.size());
		return  new ResponseEntity<>(loanUsers, HttpStatus.OK);
	}
	
	@GetMapping("/getLoanUsers")
	public ResponseEntity<List<LoanUser>> getLoanUser(){		
		logger.debug("Entered request for find all Loan users");
		return new ResponseEntity<> (loanUsersService.findAllUsers(), HttpStatus.OK);
	}
	
	@GetMapping("/getLoanUserByFirstName/{userFirstName}")
	public ResponseEntity<LoanUser> getLoanUserByFirstName(@PathVariable String userFirstName) throws LoanUserNotFoundException{	
		logger.info("Search request for Loan with firstName :{} ", userFirstName);
		return new ResponseEntity<> (loanUsersService.findByFirstName(userFirstName), HttpStatus.OK);
	}
	
	@GetMapping("/getLoanUserByLastName/{userLastName}")
	public ResponseEntity<LoanUser> getLoanUserByLastName(@PathVariable String userLastName) throws LoanUserNotFoundException{		
		logger.info("Search request for Loan with Last Name :{} ", userLastName);
		return new ResponseEntity<> (loanUsersService.findByLastName(userLastName), HttpStatus.OK);
	}
}
