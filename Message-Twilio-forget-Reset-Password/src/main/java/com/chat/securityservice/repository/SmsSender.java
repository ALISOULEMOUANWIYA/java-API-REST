package com.chat.securityservice.repository;

import com.chat.securityservice.entities.PasswordResetRequestDto;
import com.chat.securityservice.entities.PasswordResetResponseDto;

import reactor.core.publisher.Mono;

public interface SmsSender {
	Mono<PasswordResetResponseDto> sendSms(PasswordResetRequestDto request);
	
	Mono<String> validateOTP(String userName, String userInputOtp);
}
