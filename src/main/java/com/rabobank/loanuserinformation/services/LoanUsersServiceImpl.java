/**
 * 
 */
package com.rabobank.loanuserinformation.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rabobank.loanuserinformation.Repository.LoanUsersRepository;
import com.rabobank.loanuserinformation.exceptions.LoanUserNotFoundException;
import com.rabobank.loanuserinformation.exceptions.UserDetailsAlreadyExistForEmailIDException;
import com.rabobank.loanuserinformation.model.Address;
import com.rabobank.loanuserinformation.model.LoanUser;
import com.rabobank.loanuserinformation.request.LoanUserRequest;

/**
 * @author Admin
 *
 */
@Service
public class LoanUsersServiceImpl implements LoanUsersService {
	private static final Logger logger = LoggerFactory.getLogger(LoanUsersServiceImpl.class);
	
	@Autowired
	LoanUsersRepository loanUsersRepository;
	

	@Override
	public LoanUser addLoanUser(LoanUserRequest addUserRequest) throws UserDetailsAlreadyExistForEmailIDException {
		logger.info("Entered addLoanUser() for new user with first Name {}", addUserRequest.getUserFirstname());
		if (Boolean.TRUE.equals(loanUsersRepository.existsByUserEmail(addUserRequest.getUserEmail()))) {
			logger.info("User Details already exists for the email Id {}", addUserRequest.getUserEmail());
			throw new UserDetailsAlreadyExistForEmailIDException(
					"User Details already exists for the email Id" + addUserRequest.getUserEmail());
		}
		LoanUser loanUser =new LoanUser(addUserRequest.getUserFirstname(), addUserRequest.getUserLastname() ,addUserRequest.getUserEmail());
		
		Address address = new Address(addUserRequest.getPropertyAddress().getAddressLine1(), addUserRequest.getPropertyAddress().getAddressLine2(), addUserRequest.getPropertyAddress().getAddressLine3(), 
				addUserRequest.getPropertyAddress().getCity(), addUserRequest.getPropertyAddress().getState(), addUserRequest.getPropertyAddress().getCountry());
		loanUser.setPropertyAddress(address);
		
		loanUsersRepository.save(loanUser);	
		logger.info("User Details saved successfully");
		return loanUser;
	}
	
	@Override
	public List<LoanUser> findAllUsers() {
		logger.info("Entered findAllUsers()");
		return loanUsersRepository.findAll();
	}

	@Override
	public LoanUser findByFirstName(String userFirstName) throws LoanUserNotFoundException {
		return loanUsersRepository.findByUserFirstname(userFirstName)
				.orElseThrow(() -> new LoanUserNotFoundException("Loan User Not Found with user Firstname: " + userFirstName));
	}


	@Override
	public LoanUser findByLastName(String userLastName) throws LoanUserNotFoundException {
		
		return loanUsersRepository.findByUserLastname(userLastName)
				.orElseThrow(() -> new LoanUserNotFoundException("Loan User Not Found with user Lastname: " + userLastName));
	}
}
