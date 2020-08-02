/**
 * 
 */
package com.rabobank.borrowerinformation.services;

import java.util.List;

import com.rabobank.borrowerinformation.exceptions.BorrowerNotFoundException;
import com.rabobank.borrowerinformation.exceptions.BorrowerDetailsAlreadyExistForEmailIDException;
import com.rabobank.borrowerinformation.model.Borrower;
import com.rabobank.borrowerinformation.request.BorrowerRequest;


/**
 * @author Admin
 *
 */
public interface BorrowerService {
	
	public Borrower addBorrower(BorrowerRequest borrowerRequest) throws BorrowerDetailsAlreadyExistForEmailIDException;
	
	public List<Borrower> findAllBorrowers();
	
	public Borrower findByFirstName(String userFirstName) throws BorrowerNotFoundException;
	
	public Borrower findByLastName(String userLastName) throws BorrowerNotFoundException;
	
	public List<String> getAllBorrowerEmails();
}
