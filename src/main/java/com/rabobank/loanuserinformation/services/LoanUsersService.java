/**
 * 
 */
package com.rabobank.loanuserinformation.services;

import java.util.List;

import com.rabobank.loanuserinformation.exceptions.LoanUserNotFoundException;
import com.rabobank.loanuserinformation.exceptions.UserDetailsAlreadyExistForEmailIDException;
import com.rabobank.loanuserinformation.model.LoanUser;
import com.rabobank.loanuserinformation.request.LoanUserRequest;


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
