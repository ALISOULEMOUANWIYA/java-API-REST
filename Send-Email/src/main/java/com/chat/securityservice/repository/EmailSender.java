package com.chat.securityservice.repository;

import com.chat.securityservice.entities.EmailResetRequestDto;
import com.chat.securityservice.entities.EmailResetResponseDto;

import reactor.core.publisher.Mono;

public interface EmailSender {
	Mono<EmailResetResponseDto> sendEmail(EmailResetRequestDto request);
	
	Mono<String> validateOTP(String userName, String userInputOtp);
}
