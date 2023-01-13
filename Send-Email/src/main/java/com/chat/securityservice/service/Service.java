package com.chat.securityservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.chat.securityservice.entities.EmailResetRequestDto;
import com.chat.securityservice.entities.EmailResetResponseDto;
import com.chat.securityservice.repository.EmailSender;

import reactor.core.publisher.Mono;

@org.springframework.stereotype.Service
public class Service {
	private final EmailSender SmsSender;
	
	@Autowired
	public Service(@Qualifier("twilio") TwilioOTPService SmsSender) {
		this.SmsSender =  SmsSender;
	}
	
	public Mono<EmailResetResponseDto> sendEmail(EmailResetRequestDto smsRequiRequest) {
		return getSmsSender().sendEmail(smsRequiRequest);
	}
	
	public Mono<String> validateOTP(String userName, String userInputOtp){
		return  getSmsSender().validateOTP(userName, userInputOtp);
	}

	public EmailSender getSmsSender() {
		return SmsSender;
	}
}
