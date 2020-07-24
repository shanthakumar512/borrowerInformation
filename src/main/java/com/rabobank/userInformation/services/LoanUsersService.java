/**
 * 
 */
package com.rabobank.userInformation.services;

import java.util.List;

import com.rabobank.userInformation.exceptions.LoanUserNotFoundException;
import com.rabobank.userInformation.exceptions.UserDetailsAlreadyExistForEmailIDException;
import com.rabobank.userInformation.model.LoanUser;
import com.rabobank.userInformation.request.LoanUserRequest;


/**
 * @author Admin
 *
 */
public interface LoanUsersService {
	
	public String addLoanUser(LoanUserRequest loanUserRequest) throws UserDetailsAlreadyExistForEmailIDException;
	
	public List<LoanUser> findAllUsers();
	
	public LoanUser findByFirstName(String userFirstName) throws LoanUserNotFoundException;
	
	public LoanUser findByLastName(String userLastName) throws LoanUserNotFoundException;
}
