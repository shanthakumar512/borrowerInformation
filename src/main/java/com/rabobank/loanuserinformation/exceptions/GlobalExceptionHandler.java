package com.rabobank.loanuserinformation.exceptions;

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

import com.rabobank.loanuserinformation.model.ErrorResponse;


/**
 * 
 * @author Shanthakumar
 *
 */

@ControllerAdvice
public class GlobalExceptionHandler   extends ResponseEntityExceptionHandler{
	Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(LoanUserNotFoundException.class)
	public @ResponseBody ResponseEntity<ErrorResponse> handleLoanUserNotFoundException( LoanUserNotFoundException exception) {
    	 List<String> details = new ArrayList<>();
         details.add(exception.getLocalizedMessage());
    	ErrorResponse errorResponse = new ErrorResponse("User Information not found for given input ", details);
    	
		log.error("User Information not found for given Email: {}", details);
		return ResponseEntity.badRequest().body(errorResponse);
	}
    
    @ExceptionHandler(UserDetailsAlreadyExistForEmailIDException.class)
   	public @ResponseBody ResponseEntity<ErrorResponse> handleUserDetailsAlreadyExistForEmailIDException( UserDetailsAlreadyExistForEmailIDException exception) {
       	 List<String> details = new ArrayList<>();
            details.add(exception.getLocalizedMessage());
       	ErrorResponse errorResponse = new ErrorResponse("User Details already exists for email", details);
       	
   		log.error("User Details already exists for email :{}", details);
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