package com.rabobank.borrowerinformation.exceptions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.rabobank.borrowerinformation.model.ErrorResponse;


/**
 * 
 * @author Shanthakumar
 *
 */

@ControllerAdvice
public class GlobalExceptionHandler   extends ResponseEntityExceptionHandler{
	Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BorrowerNotFoundException.class)
	public @ResponseBody ResponseEntity<ErrorResponse> handleLoanBorrowerNotFoundException( BorrowerNotFoundException exception) {
    	 List<String> details = new ArrayList<>();
         details.add(exception.getLocalizedMessage());
    	ErrorResponse errorResponse = new ErrorResponse("Borrower Information not found for given input ", details);
    	
		log.error("Borrower Information not found for given Email: {}", details);
		return ResponseEntity.badRequest().body(errorResponse);
	}
    
    @ExceptionHandler(BorrowerDetailsAlreadyExistForEmailIDException.class)
   	public @ResponseBody ResponseEntity<ErrorResponse> handleBorrowerDetailsAlreadyExistForEmailIDException( BorrowerDetailsAlreadyExistForEmailIDException exception) {
       	 List<String> details = new ArrayList<>();
            details.add(exception.getLocalizedMessage());
       	ErrorResponse errorResponse = new ErrorResponse("Borrower Details already exists for email", details);
       	
   		log.error("Borrower Details already exists for email :{}", details);
   		return ResponseEntity.badRequest().body(errorResponse);
   	}
    
    @ResponseStatus()
	public final @ResponseBody  ResponseEntity<ErrorResponse> handleResponseStatusException(final ResponseStatusException exception,
			final HttpServletRequest request) {
    	
    	 List<String> details = new ArrayList<>();
         details.add(exception.getLocalizedMessage());
         ErrorResponse error = new ErrorResponse(exception.getStatus().toString(), details);
         return new ResponseEntity<>(error, exception.getStatus());
	}   
    
    @ExceptionHandler(Exception.class)
   	public @ResponseBody ResponseEntity<ErrorResponse> handleException( Exception exception) {
       	 List<String> details = new ArrayList<>();
            details.add(exception.getLocalizedMessage());
       	ErrorResponse errorResponse = new ErrorResponse("Exception Occured", details);
       	
   		log.error("Exception occured: {}", details);
   		return ResponseEntity.badRequest().body(errorResponse);
   	}
    
}