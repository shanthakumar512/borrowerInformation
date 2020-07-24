package com.rabobank.userInformation.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rabobank.userInformation.model.LoanUser;

@Repository
public interface LoanUsersRepository extends JpaRepository<LoanUser, Long> {
	
	Optional<LoanUser> findByUserFirstname(String userFirstName);
	Optional<LoanUser> findByUserLastname(String userLastName);
	Optional<LoanUser> findByUserEmail(String userEmail);
	Boolean existsByUserEmail(String userEmail);
}
