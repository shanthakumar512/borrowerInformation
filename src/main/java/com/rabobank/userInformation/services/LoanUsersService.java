/**
 * 
 */
package com.rabobank.userinformation.services;

import java.util.List;

import com.rabobank.userinformation.exceptions.LoanUserNotFoundException;
import com.rabobank.userinformation.exceptions.UserDetailsAlreadyExistForEmailIDException;
import com.rabobank.userinformation.model.LoanUser;
import com.rabobank.userinformation.request.LoanUserRequest;


/**
 * @author Admin
 *
 */
public interface LoanUsersService {
	
	public LoanUser addLoanUser(LoanUserRequest loanUserRequest) throws UserDetailsAlreadyExistForEmailIDException;
	
	public List<LoanUser> findAllUsers();
	
	public LoanUser findByFirstName(String userFirstName) throws LoanUserNotFoundException;
	
	public LoanUser findByLastName(String userLastName) throws LoanUserNotFoundException;
}
