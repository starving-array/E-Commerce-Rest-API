package com.OrderManagement.module;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userId;

	@NotBlank(message = "Firstname can't be blank")
	private String FirstName;
	@NotBlank(message = "Lasstname can't be blank")
	private String LastName;
	

	@Size(max = 10,min = 10, message = "Phone should be 10 char")
	private String contact;
	
	@Email(message = "Enter correct email format")
	private String email;
	
	
	@Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,12}$", message = "Please enter a valid password which include upperCase lowerCase specialCharector number and character between 8 to 12")
	private String password;
	
	@Past(message = "Date of brith can't be in future")
	private LocalDate date_of_birth;

	@JsonIgnore
	private LocalDateTime registrationDate;
	@JsonIgnore
	private LocalDateTime dateModified;

	@JsonIgnore
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private List<Orders> orders = new ArrayList<>();

	@JsonIgnore
	@OneToOne(mappedBy = "userC", cascade = CascadeType.ALL)
	private Usercart cart;

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Feedback> feedbacks = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Rating> ratings = new ArrayList<>();
}
