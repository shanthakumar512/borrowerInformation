package com.rabobank.userinformation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;

import com.rabobank.userinformation.Repository.LoanUsersRepository;
import com.rabobank.userinformation.exceptions.LoanUserNotFoundException;
import com.rabobank.userinformation.exceptions.UserDetailsAlreadyExistForEmailIDException;
import com.rabobank.userinformation.model.Address;
import com.rabobank.userinformation.model.ErrorResponse;
import com.rabobank.userinformation.model.LoanUser;
import com.rabobank.userinformation.request.LoanUserRequest;
import com.rabobank.userinformation.services.LoanUsersService;


@SpringBootTest(classes = {LoanUserInformationApplication.class, H2JpaConfig.class },
webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@ActiveProfiles("test")
class LoanUserInformationApplicationIntegrationTests {

	@Autowired 
	LoanUsersRepository loanUsersRepository;
	
	@Autowired 
	LoanUsersService loanUsersService;
	
	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}
	
	
	@Test
	void contextLoads() {
	}
	
	@Test
	@Rollback(false)
	void controllerAddLoanUserFromTest() {
		LoanUserRequest addUserRequest= new LoanUserRequest();
		addUserRequest.setUserFirstname("user1");
		addUserRequest.setUserLastname("user1");
		addUserRequest.setUserEmail("abcd@gmail.co");
		Address propertyAddress = new Address();
		propertyAddress.setAddressLine1("a1");
		propertyAddress.setAddressLine2("a2");
		propertyAddress.setAddressLine3("a3");
		propertyAddress.setCity("city");
		propertyAddress.setState("TN");
		propertyAddress.setCountry("Ind");
		addUserRequest.setPropertyAddress(propertyAddress);
		ResponseEntity<LoanUserRequest[]> postResponse = restTemplate.postForEntity(getRootUrl() + "/loanUser/addLoanUser", addUserRequest, LoanUserRequest[].class);
		
		assertNotNull(postResponse.getBody());
	}
	
	@Test
	@Rollback(false)
	void controllerAddLoanUserControllerAdviceTest() {
		LoanUserRequest addUserRequest= new LoanUserRequest();
		addUserRequest.setUserFirstname("user9");
		addUserRequest.setUserLastname("user9");
		addUserRequest.setUserEmail("user9@gmail.com");
		Address propertyAddress = new Address();
		propertyAddress.setAddressLine1("a1");
		propertyAddress.setAddressLine2("a2");
		propertyAddress.setAddressLine3("a3");
		propertyAddress.setCity("city");
		propertyAddress.setState("TN");
		propertyAddress.setCountry("Ind");
		addUserRequest.setPropertyAddress(propertyAddress);
		 Assertions.assertThrows(RestClientException.class,()-> restTemplate.postForEntity(getRootUrl() + "/loanUser/addLoanUser", addUserRequest, LoanUser[].class));
	}
		
	@Test
	@Rollback(false)
	void controllerfindAllUsersTest() {

		ResponseEntity<LoanUserRequest[]> response = restTemplate.getForEntity(getRootUrl() + "/loanUser/getLoanUsers", LoanUserRequest[].class);
		 assertEquals(loanUsersRepository.findAll().size(), response.getBody().length);
	}
	
	@Test
	@Rollback(false)
	void controllerfindByFirstNameTest() {
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("userFirstName", "user10");
		LoanUserRequest addUserRequest = new LoanUserRequest();
		addUserRequest.setUserFirstname("user10");
		addUserRequest.setUserLastname("user10");
		addUserRequest.setUserEmail("user10@gmail.com");
		Address propertyAddress = new Address();
		propertyAddress.setAddressLine1("a1");
		propertyAddress.setAddressLine2("a2");
		propertyAddress.setAddressLine3("a3");
		propertyAddress.setCity("city");
		propertyAddress.setState("TN");
		propertyAddress.setCountry("Ind");
		addUserRequest.setPropertyAddress(propertyAddress);
		restTemplate.postForEntity(getRootUrl() + "/loanUser/addLoanUser", addUserRequest, LoanUserRequest[].class);
		ResponseEntity<LoanUserRequest> response = restTemplate.getForEntity(
				getRootUrl() + "/loanUser/getLoanUserByFirstName/{userFirstName}", LoanUserRequest.class, uriVariables);
		assertEquals(response.getBody().getUserFirstname(), "user10");
	}
	
	
	@Test
	@Rollback(false)
	void controllerfindByLastNameTest() {
		LoanUserRequest addUserRequest= new LoanUserRequest();
		addUserRequest.setUserFirstname("user9");
		addUserRequest.setUserLastname("user9");
		addUserRequest.setUserEmail("user9@gmail.com");
		Address propertyAddress = new Address();
		propertyAddress.setAddressLine1("a1");
		propertyAddress.setAddressLine2("a2");
		propertyAddress.setAddressLine3("a3");
		propertyAddress.setCity("city");
		propertyAddress.setState("TN");
		propertyAddress.setCountry("Ind");
		addUserRequest.setPropertyAddress(propertyAddress);
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("userLastName", "user9");
		restTemplate.postForEntity(getRootUrl() + "/loanUser/addLoanUser", addUserRequest, LoanUserRequest[].class);
		ResponseEntity<LoanUserRequest> response = restTemplate.getForEntity(getRootUrl() + "/loanUser/getLoanUserByLastName/{userLastName}", LoanUserRequest.class,uriVariables);
		assertEquals(response.getBody().getUserLastname(), "user9");
	}

	@Test
	@Rollback(false)
	void controllerfindByLastNameControllerAdviceTest(){
		 Map<String, String> uriVariables = new HashMap<>();
		 uriVariables.put("userLastName", "user1");
		
		 restTemplate.getForEntity(getRootUrl() + "/loanUser/getLoanUserByLastName/{userLastName}", LoanUserRequest.class,uriVariables);
	}
	
	@Test
	@Rollback(false)
	void serviceaddLoanUserTest() throws UserDetailsAlreadyExistForEmailIDException {
		LoanUserRequest addUserRequest= new LoanUserRequest();
		addUserRequest.setUserFirstname("user5");
		addUserRequest.setUserLastname("user5");
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
		Optional<LoanUser> info =loanUsersRepository.findByUserFirstname("user5");
		assertTrue(info.isPresent());
	}
	
	@Test
	@Rollback(false)
	void serviceaddLoanUserWithAlreadyExistingEmailTest() {
		LoanUserRequest addUserRequest= new LoanUserRequest();
		addUserRequest.setUserFirstname("user6");
		addUserRequest.setUserLastname("user6");
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
	void serviceaddSecondLoanUserTest() throws UserDetailsAlreadyExistForEmailIDException {
		LoanUserRequest addUserRequest= new LoanUserRequest();
		addUserRequest.setUserFirstname("user6");
		addUserRequest.setUserLastname("user6");
		addUserRequest.setUserEmail("user6@gmail.com");
		Address propertyAddress = new Address();
		propertyAddress.setAddressLine1("a1");
		propertyAddress.setAddressLine2("a2");
		propertyAddress.setAddressLine3("a3");
		propertyAddress.setCity("city");
		propertyAddress.setState("TN");
		propertyAddress.setCountry("Ind");
		addUserRequest.setPropertyAddress(propertyAddress);
		loanUsersService.addLoanUser(addUserRequest);
		Optional<LoanUser> info =loanUsersRepository.findByUserFirstname("user6");
		assertTrue(info.isPresent());
	}
	
	@Test
	@Rollback(false)
	void servicefindAllUsersTest() {
		List<LoanUser> userslist= loanUsersService.findAllUsers();
		 assertNotNull(userslist);
	}
	
	@Test
	@Rollback(false)
	void servicefindByFirstNameTest() throws LoanUserNotFoundException {
		LoanUser user = loanUsersService.findByFirstName("user5");
		 assertEquals(user.getUserFirstname(), "user5");
	}
	
	@Test
	@Rollback(false)
	void servicefindByNotExistingFirstNameTest() {
		Assertions.assertThrows(LoanUserNotFoundException.class,()->loanUsersService.findByFirstName("usersdd"));
	}
	
	@Test
	@Rollback(false)
	void servicefindByLastNameTest() throws LoanUserNotFoundException {
		LoanUser user = loanUsersService.findByLastName("user5");
		 assertEquals(user.getUserLastname(), "user5");
	}
	
	
	@Test
	@Rollback(false)
	void serviceservicefindByNotExistingLastNameTest(){
		Assertions.assertThrows(LoanUserNotFoundException.class,()->loanUsersService.findByLastName("usersdd"));
	}
	
	
}
