package com.rabobank.borrowerinformation.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rabobank.borrowerinformation.model.Borrower;

@Repository
public interface BorrowersRepository extends JpaRepository<Borrower, Long> {
	
	Optional<Borrower> findByBorrowerFirstname(String userFirstName);
	
	Optional<Borrower> findByBorrowerLastname(String userLastName);
	
	Optional<Borrower> findByBorrowerEmail(String userEmail);
	
	Boolean existsByBorrowerEmail(String userEmail);
	
	@Query("SELECT DISTINCT b.borrowerEmail FROM Borrower b where b.borrowerEmail is not null")
	List<String> findAllEmails();
}
