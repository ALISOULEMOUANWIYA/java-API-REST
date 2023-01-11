package com.chat.securityservice.entities;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;


public class PasswordResetRequestDto {
	@NotBlank
	private String phoneNumber; // destination
	@NotBlank
	private String userName;
	@NotBlank
	private String oneTimePassword;
	
	public PasswordResetRequestDto(@JsonProperty("phoneNumber") String phoneNumber,
			@JsonProperty("userName")String userName, @JsonProperty("oneTimePassword") String oneTimePassword){
		this.phoneNumber = phoneNumber;
		this.userName = userName;
		this.oneTimePassword = oneTimePassword;
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
	
	@Override
	public String toString() {
		return "PasswordResetRequestDto [phoneNumber=" + phoneNumber + ", username=" + userName + ", oneTimePassword="
				+ oneTimePassword + "]";
	}
	
}
