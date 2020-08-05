package com.rabobank.borrowerinformation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.rabobank.borrowerinformation.H2JpaConfig;
import com.rabobank.borrowerinformation.BorrowerInformationApplication;
import com.rabobank.borrowerinformation.exceptions.BorrowerNotFoundException;
import com.rabobank.borrowerinformation.exceptions.BorrowerDetailsAlreadyExistForEmailIDException;
import com.rabobank.borrowerinformation.model.Address;
import com.rabobank.borrowerinformation.model.Borrower;
import com.rabobank.borrowerinformation.repository.BorrowersRepository;
import com.rabobank.borrowerinformation.request.BorrowerRequest;
import com.rabobank.borrowerinformation.services.BorrowerService;


@SpringBootTest(classes = {BorrowerInformationApplication.class, H2JpaConfig.class },
webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@ActiveProfiles("test")
class BorrowerInformationServicesTest {

	private static final String USER2 = "user2";

	private static final String USER1 = "user1";

	@Autowired 
	BorrowersRepository borrowersRepository;
	
	@Autowired 
	BorrowerService borrowerService;
	
	@Test	
	@Rollback(false)
	void addLoanUserTest() throws BorrowerDetailsAlreadyExistForEmailIDException {
		BorrowerRequest borrowerRequest= new BorrowerRequest();
		borrowerRequest.setBorrowerFirstname(USER1);
		borrowerRequest.setBorrowerLastname(USER1);
		borrowerRequest.setBorrowerEmail("shan@gmail.com");
		Address propertyAddress = new Address();
		propertyAddress.setAddressLine1("a1");
		propertyAddress.setAddressLine2("a2");
		propertyAddress.setAddressLine3("a3");
		propertyAddress.setCity("city");
		propertyAddress.setState("TN");
		propertyAddress.setCountry("Ind");
		borrowerRequest.setPropertyAddress(propertyAddress);
		borrowerService.addBorrower(borrowerRequest);
		Optional<Borrower> info =borrowersRepository.findByBorrowerFirstname(USER1);
		assertTrue(info.isPresent());
	}
	
	@Test
	@Rollback(false)
	void addLoanUserWithAlreadyExistingEmailTest() {
		BorrowerRequest borrowerRequest= new BorrowerRequest();
		borrowerRequest.setBorrowerFirstname(USER2);
		borrowerRequest.setBorrowerLastname(USER2);
		borrowerRequest.setBorrowerEmail("shan@gmail.com");
		Address propertyAddress = new Address();
		propertyAddress.setAddressLine1("a1");
		propertyAddress.setAddressLine2("a2");
		propertyAddress.setAddressLine3("a3");
		propertyAddress.setCity("city");
		propertyAddress.setState("TN");
		propertyAddress.setCountry("Ind");
		borrowerRequest.setPropertyAddress(propertyAddress);
		Assertions.assertThrows(BorrowerDetailsAlreadyExistForEmailIDException.class,()->borrowerService.addBorrower(borrowerRequest));
	}
	
	
	@Test
	@Rollback(false)
	void addSecondLoanUserTest() throws BorrowerDetailsAlreadyExistForEmailIDException {
		BorrowerRequest borrowerRequest= new BorrowerRequest();
		borrowerRequest.setBorrowerFirstname(USER2);
		borrowerRequest.setBorrowerLastname(USER2);
		borrowerRequest.setBorrowerEmail("user2@gmail.com");
		Address propertyAddress = new Address();
		propertyAddress.setAddressLine1("a1");
		propertyAddress.setAddressLine2("a2");
		propertyAddress.setAddressLine3("a3");
		propertyAddress.setCity("city");
		propertyAddress.setState("TN");
		propertyAddress.setCountry("Ind");
		borrowerRequest.setPropertyAddress(propertyAddress);
		borrowerService.addBorrower(borrowerRequest);
		Optional<Borrower> info =borrowersRepository.findByBorrowerFirstname(USER2);
		assertTrue(info.isPresent());
	}
	
	@Test
	@Rollback(false)
	void findAllUsersTest() {
		List<Borrower> userslist= borrowerService.findAllBorrowers();
		 assertEquals(borrowersRepository.count(), userslist.size());
	}
	
	@Test
	@Rollback(false)
	void findByFirstNameTest() throws BorrowerNotFoundException {
		Borrower user = borrowerService.findByFirstName(USER1);
		 assertEquals(USER1,user.getBorrowerFirstname());
	}
	
	@Test
	@Rollback(false)
	void findAllBorrowerEmails() throws BorrowerNotFoundException {
		List<String> user = borrowerService.getAllBorrowerEmails();
		 assertTrue(user.size()>0);
	}
	
	@Test
	@Rollback(false)
	void findByNotExistingFirstNameTest() {
		Assertions.assertThrows(BorrowerNotFoundException.class,()->borrowerService.findByFirstName("usersdd"));
	}
	
	@Test
	@Rollback(false)
	void findByLastNameTest() throws BorrowerNotFoundException {
		Borrower user = borrowerService.findByLastName(USER1);
		 assertEquals(USER1,user.getBorrowerLastname());
	}
	
	
	@Test
	@Rollback(false)
	void findByNotExistingLastNameTest(){
		Assertions.assertThrows(BorrowerNotFoundException.class,()->borrowerService.findByLastName("usersdd"));
	}
	
	
}
