/**
 * 
 */
package com.rabobank.borrowerinformation.controllers;

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

import com.rabobank.borrowerinformation.exceptions.BorrowerNotFoundException;
import com.rabobank.borrowerinformation.exceptions.BorrowerDetailsAlreadyExistForEmailIDException;
import com.rabobank.borrowerinformation.model.Borrower;
import com.rabobank.borrowerinformation.request.BorrowerRequest;
import com.rabobank.borrowerinformation.services.BorrowerService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author Shanthakumar
 *
 */

@RestController
@RequestMapping("/loanUser")
public class BorrowerInformationController {
	
	private static final Logger logger = LoggerFactory.getLogger(BorrowerInformationController.class);
	
	@Autowired
	BorrowerService  borrowerService; 
	
	@ApiOperation(value = "Adds the Borrower information to the Borrower Database ", response = Borrower.class, tags = "addBorrowerInformation")
	@ApiResponses(value = { 
	            @ApiResponse(code = 200, message = "Success|OK"),
	            @ApiResponse(code = 401, message = "not authorized!"), 
	            @ApiResponse(code = 403, message = "forbidden!!!"),
	            @ApiResponse(code = 404, message = "not found!!!") })
	@PostMapping("/addLoanUser")
	public ResponseEntity<List<Borrower>> addBorrowerInformation(@Valid @RequestBody BorrowerRequest borrowerRequest) throws BorrowerDetailsAlreadyExistForEmailIDException{		
		
		logger.debug("Add Borrower information request with Borrower Name {} Borrower Email {} ", borrowerRequest.getBorrowerFirstname(),borrowerRequest.getBorrowerEmail());
	
		borrowerService.addBorrower(borrowerRequest);
		logger.info("Loan Borrower information Added successfullly"); 
		List<Borrower> borrowers= borrowerService.findAllBorrowers();
		logger.debug("Total Borrowers added {}", borrowers.size());
		return  new ResponseEntity<>(borrowers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Loads all the Borrower information from the Borrower Database ", response = Borrower.class, tags = "getLoanInfoByLoanNumber")
	@ApiResponses(value = { 
	            @ApiResponse(code = 200, message = "Success|OK"),
	            @ApiResponse(code = 401, message = "not authorized!"), 
	            @ApiResponse(code = 403, message = "forbidden!!!"),
	            @ApiResponse(code = 404, message = "not found!!!") })
	@GetMapping("/getLoanUsers")
	public ResponseEntity<List<Borrower>> getLoanUser(){		
		logger.debug("Entered request for find all Loan Borrowers");
		return new ResponseEntity<> (borrowerService.findAllBorrowers(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Load the Borrower information based on first name", response = Borrower.class, tags = "getBorrowerByFirstName")
	@ApiResponses(value = { 
	            @ApiResponse(code = 200, message = "Success|OK"),
	            @ApiResponse(code = 401, message = "not authorized!"), 
	            @ApiResponse(code = 403, message = "forbidden!!!"),
	            @ApiResponse(code = 404, message = "not found!!!") })
	@GetMapping("/getLoanUserByFirstName/{userFirstName}")
	public ResponseEntity<Borrower> getBorrowerByFirstName(@PathVariable String userFirstName) throws BorrowerNotFoundException{	
		logger.info("Search request for Loan with firstName :{} ", userFirstName);
		return new ResponseEntity<> (borrowerService.findByFirstName(userFirstName), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Load the Borrower information based on first name", response = Borrower.class, tags = "getBorrowerByLastName")
	@ApiResponses(value = { 
	            @ApiResponse(code = 200, message = "Success|OK"),
	            @ApiResponse(code = 401, message = "not authorized!"), 
	            @ApiResponse(code = 403, message = "forbidden!!!"),
	            @ApiResponse(code = 404, message = "not found!!!") })
	@GetMapping("/getLoanUserByLastName/{userLastName}")
	public ResponseEntity<Borrower> getBorrowerByLastName(@PathVariable String userLastName) throws BorrowerNotFoundException{		
		logger.info("Search request for Loan with Last Name :{} ", userLastName);
		return new ResponseEntity<> (borrowerService.findByLastName(userLastName), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Load all the borrower emails from Borrower information", response = String.class, tags = "getAllBorrowerEmails")
	@ApiResponses(value = { 
	            @ApiResponse(code = 200, message = "Success|OK"),
	            @ApiResponse(code = 401, message = "not authorized!"), 
	            @ApiResponse(code = 403, message = "forbidden!!!"),
	            @ApiResponse(code = 404, message = "not found!!!") })
	@GetMapping("/getAllBorrowerEmails")
	public ResponseEntity<List<String>> getAllBorrowerEmails() {		
		return new ResponseEntity<> (borrowerService.getAllBorrowerEmails(), HttpStatus.OK);
	}
}
