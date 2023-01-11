package com.chat.securityservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.chat.securityservice.entities.PasswordResetRequestDto;
import com.chat.securityservice.entities.PasswordResetResponseDto;
import com.chat.securityservice.repository.SmsSender;

import reactor.core.publisher.Mono;

@org.springframework.stereotype.Service
public class Service {
	private final SmsSender SmsSender;
	
	@Autowired
	public Service(@Qualifier("twilio") TwilioOTPService SmsSender) {
		this.SmsSender =  SmsSender;
	}
	
	public Mono<PasswordResetResponseDto> sendSms(PasswordResetRequestDto smsRequiRequest) {
		return getSmsSender().sendSms(smsRequiRequest);
	}
	
	public Mono<String> validateOTP(String userName, String userInputOtp){
		return  getSmsSender().validateOTP(userName, userInputOtp);
	}

	public SmsSender getSmsSender() {
		return SmsSender;
	}
}
