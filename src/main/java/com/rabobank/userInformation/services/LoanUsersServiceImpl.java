/**
 * 
 */
package com.rabobank.userInformation.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.rabobank.userInformation.Repository.LoanUsersRepository;
import com.rabobank.userInformation.exceptions.LoanUserNotFoundException;
import com.rabobank.userInformation.exceptions.UserDetailsAlreadyExistForEmailIDException;
import com.rabobank.userInformation.model.Address;
import com.rabobank.userInformation.model.LoanUser;
import com.rabobank.userInformation.request.LoanUserRequest;

/**
 * @author Admin
 *
 */
@Service
public class LoanUsersServiceImpl implements LoanUsersService {
	
	@Autowired
	LoanUsersRepository loanUsersRepository;
	

	@Override
	public String addLoanUser(LoanUserRequest addUserRequest) throws UserDetailsAlreadyExistForEmailIDException {
		
		if (loanUsersRepository.existsByUserEmail(addUserRequest.getUserEmail())) {
				throw new UserDetailsAlreadyExistForEmailIDException("User Details already exists for the email Id"+addUserRequest.getUserEmail());
		}
		LoanUser loanUser =new LoanUser(addUserRequest.getUserFirstname(), addUserRequest.getUserLastname() ,addUserRequest.getUserEmail());
		
		Address address = new Address(addUserRequest.getPropertyAddress().getAddressLine1(), addUserRequest.getPropertyAddress().getAddressLine2(), addUserRequest.getPropertyAddress().getAddressLine3(), 
				addUserRequest.getPropertyAddress().getCity(), addUserRequest.getPropertyAddress().getState(), addUserRequest.getPropertyAddress().getCountry());
		loanUser.setPropertyAddress(address);
		
		loanUsersRepository.save(loanUser);	
		return ("Loan User registered successfully!");
	}
	
	@Override
	public List<LoanUser> findAllUsers() {
		return loanUsersRepository.findAll();
	}

	@Override
	public LoanUser findByFirstName(String userFirstName) throws LoanUserNotFoundException {
		
		LoanUser user = loanUsersRepository.findByUserFirstname(userFirstName)
				.orElseThrow(() -> new LoanUserNotFoundException("Loan User Not Found with user Firstname: " + userFirstName));
		return user;
	}


	@Override
	public LoanUser findByLastName(String userLastName) throws LoanUserNotFoundException {
		
		LoanUser user = loanUsersRepository.findByUserLastname(userLastName)
				.orElseThrow(() -> new LoanUserNotFoundException("Loan User Not Found with user Lastname: " + userLastName));
		return user;
	}
}
