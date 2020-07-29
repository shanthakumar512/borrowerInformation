package com.rabobank.userinformation;

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

import com.rabobank.userinformation.Repository.LoanUsersRepository;
import com.rabobank.userinformation.exceptions.LoanUserNotFoundException;
import com.rabobank.userinformation.exceptions.UserDetailsAlreadyExistForEmailIDException;
import com.rabobank.userinformation.model.Address;
import com.rabobank.userinformation.model.LoanUser;
import com.rabobank.userinformation.request.LoanUserRequest;
import com.rabobank.userinformation.services.LoanUsersService;


@SpringBootTest(classes = {LoanUserInformationApplication.class, H2JpaConfig.class },
webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@ActiveProfiles("test")
class LoanUserInformationServicesTest {

	private static final String USER2 = "user2";

	private static final String USER1 = "user1";

	@Autowired 
	LoanUsersRepository loanUsersRepository;
	
	@Autowired 
	LoanUsersService loanUsersService;
	
		
	@Test
	@Rollback(false)
	void addLoanUserTest() throws UserDetailsAlreadyExistForEmailIDException {
		LoanUserRequest addUserRequest= new LoanUserRequest();
		addUserRequest.setUserFirstname(USER1);
		addUserRequest.setUserLastname(USER1);
		addUserRequest.setUserEmail("abc@gmail.com");
		Address propertyAddress = new Address();
		propertyAddress.setAddressLine1("a1");
		propertyAddress.setAddressLine2("a2");
		propertyAddress.setAddressLine3("a3");
		propertyAddress.setCity("city");
		propertyAddress.setState("TN");
		propertyAddress.setCountry("Ind");
		addUserRequest.setPropertyAddress(propertyAddress);
		loanUsersService.addLoanUser(addUserRequest);
		Optional<LoanUser> info =loanUsersRepository.findByUserFirstname(USER1);
		assertTrue(info.isPresent());
	}
	
	@Test
	@Rollback(false)
	void addLoanUserWithAlreadyExistingEmailTest() {
		LoanUserRequest addUserRequest= new LoanUserRequest();
		addUserRequest.setUserFirstname(USER2);
		addUserRequest.setUserLastname(USER2);
		addUserRequest.setUserEmail("abc@gmail.com");
		Address propertyAddress = new Address();
		propertyAddress.setAddressLine1("a1");
		propertyAddress.setAddressLine2("a2");
		propertyAddress.setAddressLine3("a3");
		propertyAddress.setCity("city");
		propertyAddress.setState("TN");
		propertyAddress.setCountry("Ind");
		addUserRequest.setPropertyAddress(propertyAddress);
		Assertions.assertThrows(UserDetailsAlreadyExistForEmailIDException.class,()->loanUsersService.addLoanUser(addUserRequest));
	}
	
	
	@Test
	@Rollback(false)
	void addSecondLoanUserTest() throws UserDetailsAlreadyExistForEmailIDException {
		LoanUserRequest addUserRequest= new LoanUserRequest();
		addUserRequest.setUserFirstname(USER2);
		addUserRequest.setUserLastname(USER2);
		addUserRequest.setUserEmail("user2@gmail.com");
		Address propertyAddress = new Address();
		propertyAddress.setAddressLine1("a1");
		propertyAddress.setAddressLine2("a2");
		propertyAddress.setAddressLine3("a3");
		propertyAddress.setCity("city");
		propertyAddress.setState("TN");
		propertyAddress.setCountry("Ind");
		addUserRequest.setPropertyAddress(propertyAddress);
		loanUsersService.addLoanUser(addUserRequest);
		Optional<LoanUser> info =loanUsersRepository.findByUserFirstname(USER2);
		assertTrue(info.isPresent());
	}
	
	@Test
	@Rollback(false)
	void findAllUsersTest() {
		List<LoanUser> userslist= loanUsersService.findAllUsers();
		 assertEquals(2, userslist.size());
	}
	
	@Test
	@Rollback(false)
	void findByFirstNameTest() throws LoanUserNotFoundException {
		LoanUser user = loanUsersService.findByFirstName(USER1);
		 assertEquals(user.getUserFirstname(), USER1);
	}
	
	@Test
	@Rollback(false)
	void findByNotExistingFirstNameTest() {
		Assertions.assertThrows(LoanUserNotFoundException.class,()->loanUsersService.findByFirstName("usersdd"));
	}
	
	@Test
	@Rollback(false)
	void findByLastNameTest() throws LoanUserNotFoundException {
		LoanUser user = loanUsersService.findByLastName(USER1);
		 assertEquals(user.getUserLastname(), USER1);
	}
	
	
	@Test
	@Rollback(false)
	void findByNotExistingLastNameTest(){
		Assertions.assertThrows(LoanUserNotFoundException.class,()->loanUsersService.findByLastName("usersdd"));
	}
	
	
}
