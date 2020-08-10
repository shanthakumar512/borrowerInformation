package com.rabobank.borrowerinformation.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(	name = "loan_users",
uniqueConstraints = { 
		@UniqueConstraint(columnNames = "borrowerEmail")})
public class Borrower {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(max = 20)
	private String borrowerFirstname;
	@NotBlank
	@Size(max = 20)
	private String borrowerLastname;
	
	public Borrower(){
		
	}

	
	public Borrower(@NotBlank @Size(max = 20) String borrowerFirstname,
			@NotBlank @Size(max = 20) String borrowerLastname, @NotBlank @Size(max = 50) String borrowerEmail) {
		super();
		this.borrowerFirstname = borrowerFirstname;
		this.borrowerLastname = borrowerLastname;
		this.borrowerEmail = borrowerEmail;
	}
	/**
	 * @return the propertyAddress
	 */
	public Address getPropertyAddress() {
		return propertyAddress;
	}
	/**
	 * @param propertyAddress the propertyAddress to set
	 */
	public void setPropertyAddress(Address propertyAddress) {
		this.propertyAddress = propertyAddress;
	}

	@NotBlank
	@Size(max = 50)
	private String borrowerEmail;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinTable(name="Borrower_Address", joinColumns= @JoinColumn(name="id"), inverseJoinColumns= @JoinColumn(name="id"))
	@JsonManagedReference
	private Address propertyAddress; 
	
	public String getBorrowerFirstname() {
		return borrowerFirstname;
	}
	public void setBorrowerFirstname(String borrowerFirstname) {
		this.borrowerFirstname = borrowerFirstname;
	}
	public String getBorrowerLastname() {
		return borrowerLastname;
	}
	public void setBorrowerLastname(String borrowerLastname) {
		this.borrowerLastname = borrowerLastname;
	}
	public String getBorrowerEmail() {
		return borrowerEmail;
	}
	public void setBorrowerEmail(String borrowerEmail) {
		this.borrowerEmail = borrowerEmail;
	}
	

}
