/**
 * 
 */
package com.rabobank.userInformation.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rabobank.userInformation.exceptions.LoanUserNotFoundException;
import com.rabobank.userInformation.exceptions.UserDetailsAlreadyExistForEmailIDException;
import com.rabobank.userInformation.model.LoanUser;

import com.rabobank.userInformation.request.LoanUserRequest;
import com.rabobank.userInformation.services.LoanUsersService;

/**
 * @author Shanthakumar
 *
 */

@RestController
@RequestMapping("/loanUser")
public class LoanUserInformationController {
	
	@Autowired
	LoanUsersService  loanUsersService;
	
	@PostMapping("/addLoanUser")
	public ResponseEntity<?> addLoanUser(@Valid @RequestBody LoanUserRequest loanUserRequest) throws UserDetailsAlreadyExistForEmailIDException{		
		loanUsersService.addLoanUser(loanUserRequest);
		return new ResponseEntity<String> ("Loan User added sucessfully!", HttpStatus.OK);
	}
	
	@GetMapping("/getLoanUsers")
	public ResponseEntity<?> getLoanUser(){		
		
		return new ResponseEntity<List<LoanUser>> (loanUsersService.findAllUsers(), HttpStatus.OK);
	}
	
	@GetMapping("/getLoanUserByFirstName/{userFirstName}")
	public ResponseEntity<?> getLoanUserByFirstName(@PathVariable String userFirstName) throws LoanUserNotFoundException{			
		return new ResponseEntity<LoanUser> (loanUsersService.findByFirstName(userFirstName), HttpStatus.OK);
	}
	
	@GetMapping("/getLoanUserByLastName/{userLastName}")
	public ResponseEntity<?> getLoanUserByLastName(@PathVariable String userLastName) throws LoanUserNotFoundException{		
		
		return new ResponseEntity<LoanUser> (loanUsersService.findByLastName(userLastName), HttpStatus.OK);
	}
}
