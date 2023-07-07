package com.chat.Service.entity;
/**
 * @author ali
 *
 */
import java.util.ArrayList;
import java.util.Collection;
//import java.sql.Blob;
//import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
//import javax.validation.constraints.*;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersE {
    
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
	
	
	//@Length(max = 9, min = 7)
	@NotBlank(message = "Please add User phone number")
	@Column(name ="phone", nullable = false)
	private String phone;
	
	@NotBlank(message = "Please add User First Nam")
	@Column(name ="name",length = 100, nullable = false)
	private String name;
	
	@NotBlank(message = "Please add User User Last name")
	@Column(name ="prenom",length = 150, nullable = false)
	private String prenom;

	@NotBlank(message = "Please add User User Last name")
	@Column(name ="sertified", nullable = false)
	private Boolean sertified;
	
	@Email
	@NotBlank(message = "Please add User Email")
	@Column(name ="email", length = 150, nullable = false, unique = true)
	private String email;
	
	@NotBlank(message = "Please add User Password")
	@Column(name ="password",length = 200, nullable = false)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	

	
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Collection<Roles> roles = new ArrayList<>(); 
}
