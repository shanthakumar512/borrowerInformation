package com.rabobank.borrowerinformation;

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
import org.springframework.web.client.RestClientException;

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
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class })
class BorrowerApplicationIntegrationTests {

	@Autowired 
	BorrowersRepository borrowersRepository;
	
	@Autowired 
	BorrowerService borrowerService;
	
	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	@Rollback(false)
	void controllerAddLoanUserFromTest() {
		BorrowerRequest borrowerRequest= new BorrowerRequest();
		borrowerRequest.setBorrowerFirstname("user1");
		borrowerRequest.setBorrowerLastname("user1");
		borrowerRequest.setBorrowerEmail("abcd@gmail.co");
		Address propertyAddress = new Address();
		propertyAddress.setAddressLine1("a1");
		propertyAddress.setAddressLine2("a2");
		propertyAddress.setAddressLine3("a3");
		propertyAddress.setCity("city");
		propertyAddress.setState("TN");
		propertyAddress.setCountry("Ind");
		borrowerRequest.setPropertyAddress(propertyAddress);
		ResponseEntity<BorrowerRequest[]> postResponse = restTemplate.postForEntity(getRootUrl() + "/loanUser/addLoanUser", borrowerRequest, BorrowerRequest[].class);
		
		assertNotNull(postResponse.getBody());
	}
	
	@Test
	@Rollback(false)
	void controllerAddLoanUserControllerAdviceTest() {
		BorrowerRequest borrowerRequest= new BorrowerRequest();
		borrowerRequest.setBorrowerFirstname("user9");
		borrowerRequest.setBorrowerLastname("user9");
		borrowerRequest.setBorrowerEmail("user9@gmail.com");
		Address propertyAddress = new Address();
		propertyAddress.setAddressLine1("a1");
		propertyAddress.setAddressLine2("a2");
		propertyAddress.setAddressLine3("a3");
		propertyAddress.setCity("city");
		propertyAddress.setState("TN");
		propertyAddress.setCountry("Ind");
		borrowerRequest.setPropertyAddress(propertyAddress);
		String URL=getRootUrl() + "/loanUser/addLoanUser";
		 Assertions.assertThrows(RestClientException.class,()-> restTemplate.postForEntity(URL, borrowerRequest, Borrower[].class));
	}
		
	@Test
	@Rollback(false)
	void controllerfindAllUsersTest() {

		ResponseEntity<Borrower[]> response = restTemplate.getForEntity(getRootUrl() + "/loanUser/getLoanUsers", Borrower[].class);
		 assertEquals(borrowersRepository.findAll().size(), response.getBody().length);
	}
	
	@Test
	@Rollback(false)
	void controllerfindByFirstNameTest() {
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("userFirstName", "user10");
		BorrowerRequest borrowerRequest = new BorrowerRequest();
		borrowerRequest.setBorrowerFirstname("user10");
		borrowerRequest.setBorrowerLastname("user10");
		borrowerRequest.setBorrowerEmail("user10@gmail.com");
		Address propertyAddress = new Address();
		propertyAddress.setAddressLine1("a1");
		propertyAddress.setAddressLine2("a2");
		propertyAddress.setAddressLine3("a3");
		propertyAddress.setCity("city");
		propertyAddress.setState("TN");
		propertyAddress.setCountry("Ind");
		borrowerRequest.setPropertyAddress(propertyAddress);
		restTemplate.postForEntity(getRootUrl() + "/loanUser/addLoanUser", borrowerRequest, Borrower[].class);
		ResponseEntity<Borrower> response = restTemplate.getForEntity(
				getRootUrl() + "/loanUser/getLoanUserByFirstName/{userFirstName}", Borrower.class, uriVariables);
		assertEquals("user10",response.getBody().getBorrowerFirstname());
	}
	
	
	@Test
	@Rollback(false)
	void controllerfindByLastNameTest() {
		BorrowerRequest borrowerRequest= new BorrowerRequest();
		borrowerRequest.setBorrowerFirstname("user9");
		borrowerRequest.setBorrowerLastname("user9");
		borrowerRequest.setBorrowerEmail("user9@gmail.com");
		Address propertyAddress = new Address();
		propertyAddress.setAddressLine1("a1");
		propertyAddress.setAddressLine2("a2");
		propertyAddress.setAddressLine3("a3");
		propertyAddress.setCity("city");
		propertyAddress.setState("TN");
		propertyAddress.setCountry("Ind");
		borrowerRequest.setPropertyAddress(propertyAddress);
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("userLastName", "user9");
		restTemplate.postForEntity(getRootUrl() + "/loanUser/addLoanUser", borrowerRequest, Borrower[].class);
		ResponseEntity<Borrower> response = restTemplate.getForEntity(getRootUrl() + "/loanUser/getLoanUserByLastName/{userLastName}", Borrower.class,uriVariables);
		assertEquals("user9",response.getBody().getBorrowerLastname());
	}

	@Test
	@Rollback(false)
	void controllerfindByLastNameControllerAdviceTest(){
		 Map<String, String> uriVariables = new HashMap<>();
		 uriVariables.put("userLastName", "user1");
		
		 restTemplate.getForEntity(getRootUrl() + "/loanUser/getLoanUserByLastName/{userLastName}", Borrower.class,uriVariables);
		 assertNotNull(restTemplate);
	}
	
	@Test
	@Rollback(false)
	void serviceaddLoanUserTest() throws BorrowerDetailsAlreadyExistForEmailIDException {
		BorrowerRequest borrowerRequest= new BorrowerRequest();
		borrowerRequest.setBorrowerFirstname("borrower5");
		borrowerRequest.setBorrowerLastname("borrower5");
		borrowerRequest.setBorrowerEmail("borrower5@gmail.com");
		Address propertyAddress = new Address();
		propertyAddress.setAddressLine1("a1");
		propertyAddress.setAddressLine2("a2");
		propertyAddress.setAddressLine3("a3");
		propertyAddress.setCity("city");
		propertyAddress.setState("TN");
		propertyAddress.setCountry("Ind");
		borrowerRequest.setPropertyAddress(propertyAddress);
		borrowerService.addBorrower(borrowerRequest);
		Optional<Borrower> info =borrowersRepository.findByBorrowerFirstname("borrower5");
		assertTrue(info.isPresent());
	}
	
	@Test
	@Rollback(false)
	void serviceaddLoanUserWithAlreadyExistingEmailTest() {
		BorrowerRequest borrowerRequest= new BorrowerRequest();
		borrowerRequest.setBorrowerFirstname("user6");
		borrowerRequest.setBorrowerLastname("user6");
		borrowerRequest.setBorrowerEmail("abc@gmail.com");
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
	void serviceaddSecondLoanUserTest() throws BorrowerDetailsAlreadyExistForEmailIDException {
		BorrowerRequest borrowerRequest= new BorrowerRequest();
		borrowerRequest.setBorrowerFirstname("user6");
		borrowerRequest.setBorrowerLastname("user6");
		borrowerRequest.setBorrowerEmail("user6@gmail.com");
		Address propertyAddress = new Address();
		propertyAddress.setAddressLine1("a1");
		propertyAddress.setAddressLine2("a2");
		propertyAddress.setAddressLine3("a3");
		propertyAddress.setCity("city");
		propertyAddress.setState("TN");
		propertyAddress.setCountry("Ind");
		borrowerRequest.setPropertyAddress(propertyAddress);
		borrowerService.addBorrower(borrowerRequest);
		Optional<Borrower> info =borrowersRepository.findByBorrowerFirstname("user6");
		assertTrue(info.isPresent());
	}
	
	@Test
	@Rollback(false)
	void servicefindAllUsersTest() {
		List<Borrower> userslist= borrowerService.findAllBorrowers();
		 assertNotNull(userslist);
	}
	
	
	@Test
	void servicefindByNotExistingFirstNameTest() {
		Assertions.assertThrows(BorrowerNotFoundException.class,()->borrowerService.findByFirstName("usersdd"));
	}
	
	@Test
	@Rollback(false)
	void servicefindByLastNameTest() throws BorrowerNotFoundException {
		Borrower user = borrowerService.findByLastName("borrower5");
		 assertEquals("borrower5",user.getBorrowerLastname());
	}
	
	
	@Test
	@Rollback(false)
	void serviceservicefindByNotExistingLastNameTest(){
		Assertions.assertThrows(BorrowerNotFoundException.class,()->borrowerService.findByLastName("usersdd"));
	}
	
	@Test
	@Rollback(false)
	void findAllEmailsFromControllerTest() {

		ResponseEntity<String[]> response = restTemplate.getForEntity(getRootUrl() + "/loanUser/getAllBorrowerEmails", String[].class);
		 assertEquals(borrowersRepository.findAllEmails().size(), response.getBody().length);
	}
	
	
}
