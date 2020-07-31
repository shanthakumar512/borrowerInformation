package com.rabobank.loanuserinformation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.HashMap;
import java.util.Map;
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

import com.rabobank.loanuserinformation.Repository.LoanUsersRepository;
import com.rabobank.loanuserinformation.exceptions.LoanUserNotFoundException;
import com.rabobank.loanuserinformation.model.Address;
import com.rabobank.loanuserinformation.request.LoanUserRequest;
import com.rabobank.loanuserinformation.services.LoanUsersService;


@SpringBootTest(classes = {LoanUserInformationApplication.class, H2JpaConfig.class },
webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@ActiveProfiles("test")
class LoanUserInformationControllerTests {
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired 
	LoanUsersRepository loanUsersRepository;
	
	@Autowired 
	LoanUsersService loanUsersService;
	
	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}
	
	@Rollback(false)
	void addLoanUserFromControllerTest() {
		LoanUserRequest addUserRequest= new LoanUserRequest();
		addUserRequest.setUserFirstname("user1");
		addUserRequest.setUserLastname("user1");
		addUserRequest.setUserEmail("abc@gmail.com");
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
	void findAllUsersFromControllerTest() {

		ResponseEntity<LoanUserRequest[]> response = restTemplate.getForEntity(getRootUrl() + "/loanUser/getLoanUsers", LoanUserRequest[].class);
		 assertEquals(loanUsersRepository.findAll().size(), response.getBody().length);
	}
	
	@Test
	@Rollback(false)
	void findByFirstNameFromControllerTest() {
		 Map<String, String> uriVariables = new HashMap<>();
		 uriVariables.put("userFirstName", "user1");
		
		ResponseEntity<LoanUserRequest> response = restTemplate.getForEntity(getRootUrl() + "/loanUser/getLoanUserByFirstName/{userFirstName}", LoanUserRequest.class,uriVariables);
		assertEquals(response.getBody().getUserFirstname(), "user1");
	}
	
	
	@Test
	@Rollback(false)
	void findByLastNamefromControllerTest(){
		 Map<String, String> uriVariables = new HashMap<>();
		 uriVariables.put("userLastName", "user1");
		
		ResponseEntity<LoanUserRequest> response = restTemplate.getForEntity(getRootUrl() + "/loanUser/getLoanUserByLastName/{userLastName}", LoanUserRequest.class,uriVariables);
		assertEquals(response.getBody().getUserLastname(), "user1");
	}
	
	
	@Test
	@Rollback(false)
	void findByNotExistingLastNameTest(){
		Assertions.assertThrows(LoanUserNotFoundException.class,()->loanUsersService.findByLastName("usersdd"));
	}
	
	
}
