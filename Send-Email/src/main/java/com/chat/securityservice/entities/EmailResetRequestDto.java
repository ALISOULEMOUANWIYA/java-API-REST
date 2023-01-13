package com.chat.securityservice.entities;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


import com.fasterxml.jackson.annotation.JsonProperty;


public class EmailResetRequestDto {
	
	@Email
	@NotBlank(message = "Please add Email ")
	private String Email;
	
	@NotBlank
	private String subjectEmail;
	
	public EmailResetRequestDto(
			@JsonProperty("Email") String Email,
			@JsonProperty("subjectEmail") String subjectEmail
	){
		this.Email = Email;
		this.subjectEmail = subjectEmail;
	}
	


	public String getEmail() {
		return Email;
	}

	public String getSubjectEmail() {
		return subjectEmail;
	}


	@Override
	public String toString() {
		return "PasswordResetRequestDto ["
				+ ", Email=" + Email + ""
				+ ", subjectEmail=" +subjectEmail
				+"]";
	}
	
	
}
