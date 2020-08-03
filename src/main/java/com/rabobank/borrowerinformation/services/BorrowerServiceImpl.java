/**
 * 
 */
package com.rabobank.borrowerinformation.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rabobank.borrowerinformation.Repository.BorrowersRepository;
import com.rabobank.borrowerinformation.exceptions.BorrowerNotFoundException;
import com.rabobank.borrowerinformation.exceptions.BorrowerDetailsAlreadyExistForEmailIDException;
import com.rabobank.borrowerinformation.model.Address;
import com.rabobank.borrowerinformation.model.Borrower;
import com.rabobank.borrowerinformation.request.BorrowerRequest;

/**
 * @author Admin
 *
 */
@Service
public class BorrowerServiceImpl implements BorrowerService {
	private static final Logger logger = LoggerFactory.getLogger(BorrowerServiceImpl.class);
	
	@Autowired
	BorrowersRepository borrowersRepository;
	

	@Override
	public Borrower addBorrower(BorrowerRequest addUserRequest) throws BorrowerDetailsAlreadyExistForEmailIDException {
		logger.info("Entered addBorrower() for new Borrower with first Name {}", addUserRequest.getBorrowerFirstname());
		if (Boolean.TRUE.equals(borrowersRepository.existsByBorrowerEmail(addUserRequest.getBorrowerEmail()))) {
			logger.error("Borrower Details already exists for the email Id {}", addUserRequest.getBorrowerEmail());
			throw new BorrowerDetailsAlreadyExistForEmailIDException(
					"Borrower Details already exists for the email Id" + addUserRequest.getBorrowerEmail());
		}
		Borrower borrower =new Borrower(addUserRequest.getBorrowerFirstname(), addUserRequest.getBorrowerLastname() ,addUserRequest.getBorrowerEmail());
		
		Address address = new Address(addUserRequest.getPropertyAddress().getAddressLine1(), addUserRequest.getPropertyAddress().getAddressLine2(), addUserRequest.getPropertyAddress().getAddressLine3(), 
				addUserRequest.getPropertyAddress().getCity(), addUserRequest.getPropertyAddress().getState(), addUserRequest.getPropertyAddress().getCountry());
		borrower.setPropertyAddress(address);
		
		borrowersRepository.save(borrower);	
		logger.info("Borrower Details saved successfully");
		return borrower;
	}
	
	@Override
	public List<Borrower> findAllBorrowers() {
		logger.info("Entered findAllBorrowers()");
		return borrowersRepository.findAll();
	}

	@Override
	public Borrower findByFirstName(String userFirstName) throws BorrowerNotFoundException {
		return borrowersRepository.findByBorrowerFirstname(userFirstName)
				.orElseThrow(() -> new BorrowerNotFoundException("Loan Borrower Not Found with borrower Firstname: " + userFirstName));
	}


	@Override
	public Borrower findByLastName(String userLastName) throws BorrowerNotFoundException {
		
		return borrowersRepository.findByBorrowerLastname(userLastName)
				.orElseThrow(() -> new BorrowerNotFoundException("Loan Borrower Not Found with borrower Lastname: " + userLastName));
	}

	@Override
	public List<String> getAllBorrowerEmails() {
		return borrowersRepository.findAllEmails();
	}
	
}
