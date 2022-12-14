package com.OrderManagement.module;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotBlank
	private String feedbackTitle;
	@NotBlank
	private String feedbackBody;

	@JsonIgnore
	private LocalDateTime createDate;
	@JsonIgnore
	private LocalDateTime modifiedDate;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "User_Id", referencedColumnName = "userId")
	private User user;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "Product_Id", referencedColumnName = "productId")
	private Products products;
}
