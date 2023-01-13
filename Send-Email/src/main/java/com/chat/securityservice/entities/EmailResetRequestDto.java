package com.chat.securityservice.entities;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


import com.fasterxml.jackson.annotation.JsonProperty;


public class EmailResetRequestDto {
	
	

	@NotBlank
	private String phoneNumber; // destination
	@NotBlank
	private String userName;
	@NotBlank
	private String oneTimePassword;
	
	@Email
	@NotBlank(message = "Please add Email ")
	private String Email;
	
	@NotBlank
	private String subjectEmail;
	
	public EmailResetRequestDto(
			@JsonProperty("phoneNumber") String phoneNumber,
			@JsonProperty("userName")String userName, 
			@JsonProperty("oneTimePassword") String oneTimePassword,
			@JsonProperty("Email") String Email,
			@JsonProperty("subjectEmail") String subjectEmail
	){
		this.phoneNumber = phoneNumber;
		this.userName = userName;
		this.oneTimePassword = oneTimePassword;
		this.Email = Email;
		this.subjectEmail = subjectEmail;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getUserName() {
		return userName;
	}

	public String getOneTimePassword() {
		return oneTimePassword;
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
				+ "phoneNumber=" + phoneNumber 
				+ ", userName=" + userName 
				+ ", oneTimePassword="+ oneTimePassword 
				+ ", Email=" + Email + ""
				+ ", subjectEmail=" +subjectEmail
				+"]";
	}
	
	
}
