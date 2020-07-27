package com.rabobank.userinformation.exceptions;

import java.io.IOException;
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

import com.rabobank.userinformation.model.ErrorResponse;


/**
 * 
 * @author Shanthakumar
 *
 */

@ControllerAdvice
public class GlobalExceptionHandler   extends ResponseEntityExceptionHandler{
	Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
    @ExceptionHandler(IOException.class)
	public @ResponseBody ErrorResponse handleIOException( IOException exception) {
    	 List<String> details = new ArrayList<>();
         details.add(exception.getLocalizedMessage());
    	ErrorResponse errorResponse = new ErrorResponse("IO Exception", details);
    	
		log.error("IO Exception Occured: {}", exception.getMessage());
		return errorResponse;
	}
    
    @ExceptionHandler(LoanUserNotFoundException.class)
	public @ResponseBody ResponseEntity<ErrorResponse> handleLoanUserNotFoundException( LoanUserNotFoundException exception) {
    	 List<String> details = new ArrayList<>();
         details.add(exception.getLocalizedMessage());
    	ErrorResponse errorResponse = new ErrorResponse("User Information not found for given Email", details);
    	
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
    
    @ExceptionHandler(DataIntegrityViolationException.class)
   	public @ResponseBody ResponseEntity<ErrorResponse> handleFileException( DataIntegrityViolationException exception) {
       	 List<String> details = new ArrayList<>();
            details.add(exception.getLocalizedMessage());
       	ErrorResponse errorResponse = new ErrorResponse("DataIntegrityViolationException", details);
       	
   		log.error("DataIntegrityViolationException: {}", details);
   		return ResponseEntity.badRequest().body(errorResponse);
   	}
    
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
	public final @ResponseBody  ResponseEntity<ErrorResponse> handleBadRequestException(final ResponseStatusException exception,
			final HttpServletRequest request) {

    	 List<String> details = new ArrayList<>();
         details.add(exception.getLocalizedMessage());
         ErrorResponse error = new ErrorResponse("BAD_REQUEST", details);
         return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
   	public final @ResponseBody  ResponseEntity<ErrorResponse> handleInternalServer(final ResponseStatusException exception,
   			final HttpServletRequest request) {

       	 List<String> details = new ArrayList<>();
            details.add(exception.getLocalizedMessage());
            ErrorResponse error = new ErrorResponse("INTERNAL_SERVER_ERROR", details);
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
   	}
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
   	public final @ResponseBody  ResponseEntity<ErrorResponse> handleMethodNotAllowedException(final ResponseStatusException exception,
   			final HttpServletRequest request) {

       	 List<String> details = new ArrayList<>();
            details.add(exception.getLocalizedMessage());
            ErrorResponse error = new ErrorResponse("METHOD_NOT_ALLOWED", details);
            return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
            
   	}
    
       
}