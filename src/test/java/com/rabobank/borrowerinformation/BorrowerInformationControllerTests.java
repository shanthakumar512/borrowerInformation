package com.rabobank.borrowerinformation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.rabobank.borrowerinformation.H2JpaConfig;
import com.rabobank.borrowerinformation.BorrowerInformationApplication;
import com.rabobank.borrowerinformation.exceptions.BorrowerNotFoundException;
import com.rabobank.borrowerinformation.model.Address;
import com.rabobank.borrowerinformation.model.Borrower;
import com.rabobank.borrowerinformation.repository.BorrowersRepository;
import com.rabobank.borrowerinformation.request.BorrowerRequest;
import com.rabobank.borrowerinformation.services.BorrowerService;


@SpringBootTest(classes = {BorrowerInformationApplication.class, H2JpaConfig.class },
webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@ActiveProfiles("test")
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class })
class BorrowerInformationControllerTests {
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired 
	BorrowersRepository borrowersRepository;
	
	@Autowired 
	BorrowerService borrowerService;
	
	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}
	
	@Test	
	@Rollback(false)
	void addLoanUserFromControllerTest() {
		BorrowerRequest borrowerRequest= new BorrowerRequest();
		borrowerRequest.setBorrowerFirstname("user1");
		borrowerRequest.setBorrowerLastname("user1");
		borrowerRequest.setBorrowerEmail("abc@gmail.com");
		Address propertyAddress = new Address();
		propertyAddress.setAddressLine1("a1");
		propertyAddress.setAddressLine2("a2");
		propertyAddress.setAddressLine3("a3");
		propertyAddress.setCity("city");
		propertyAddress.setState("TN");
		propertyAddress.setCountry("Ind");
		borrowerRequest.setPropertyAddress(propertyAddress);
		ResponseEntity<Borrower[]> postResponse = restTemplate.postForEntity(getRootUrl() + "/loanUser/addLoanUser", borrowerRequest, Borrower[].class);
		
		assertNotNull(postResponse.getBody());
	}
		
	@Test
	@Rollback(false)
	void findAllUsersFromControllerTest() {

		ResponseEntity<Borrower[]> response = restTemplate.getForEntity(getRootUrl() + "/loanUser/getLoanUsers", Borrower[].class);
		 assertEquals(borrowersRepository.findAll().size(), response.getBody().length);
	}
	
	@Test
	@Rollback(false)
	void findByFirstNameFromControllerTest() {
		 Map<String, String> uriVariables = new HashMap<>();
		 uriVariables.put("userFirstName", "user1");
		
		ResponseEntity<Borrower> response = restTemplate.getForEntity(getRootUrl() + "/loanUser/getLoanUserByFirstName/{userFirstName}", Borrower.class,uriVariables);
		assertEquals("user1",response.getBody().getBorrowerFirstname());
	}
	
	
	@Test
	@Rollback(false)
	void findByLastNamefromControllerTest(){
		 Map<String, String> uriVariables = new HashMap<>();
		 uriVariables.put("userLastName", "user1");
		
		ResponseEntity<Borrower> response = restTemplate.getForEntity(getRootUrl() + "/loanUser/getLoanUserByLastName/{userLastName}", Borrower.class,uriVariables);
		assertEquals("user1",response.getBody().getBorrowerLastname());
	}
	
	
	@Test
	@Rollback(false)
	void findByNotExistingLastNameTest(){
		Assertions.assertThrows(BorrowerNotFoundException.class,()->borrowerService.findByLastName("usersdd"));
	}
	
	
}
