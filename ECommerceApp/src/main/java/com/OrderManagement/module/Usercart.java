package com.OrderManagement.module;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usercart {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "User_Id", referencedColumnName = "userId" )
	private User userC;
	
	@JsonIgnore
	@OneToMany(mappedBy = "usercart", cascade = CascadeType.ALL)
	private List<CartDetails> cartDetails;
	

}
