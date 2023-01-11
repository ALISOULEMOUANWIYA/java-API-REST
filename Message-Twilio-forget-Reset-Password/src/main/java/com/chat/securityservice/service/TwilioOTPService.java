package com.chat.securityservice.service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chat.securityservice.config.TwilioConfig;
import com.chat.securityservice.entities.PasswordResetRequestDto;
import com.chat.securityservice.entities.PasswordResetResponseDto;
import com.chat.securityservice.enumerable.OptStatus;
import com.chat.securityservice.repository.SmsSender;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

import reactor.core.publisher.Mono;

@Service("twilio")
public class TwilioOTPService implements SmsSender{
	
	private Map<String, String> optMap = new HashMap<>();
	
	private final static Logger LOGGER = LoggerFactory.getLogger(TwilioOTPService.class);
	
	private final TwilioConfig twilioConfig;
	
	@Autowired
	public TwilioOTPService(TwilioConfig twilioConfig) {
		this.twilioConfig = twilioConfig;
	}

	@Override
	public Mono<PasswordResetResponseDto> sendSms(PasswordResetRequestDto passwordResetRequestDto) {
		PasswordResetResponseDto passwordResetResponseDto = null; 
		if (isPhoneNumberValid(passwordResetRequestDto.getPhoneNumber())) {
			try {
				PhoneNumber to = new PhoneNumber(passwordResetRequestDto.getPhoneNumber());
				PhoneNumber from = new PhoneNumber(getTwilioConfig().getTrialNumber());
				String code = generateOTP();
				String message = "Dear Customer, Your OPT is ( LID : "+ code +" ) please Use this Passcode to Resset Your password"; ;
				MessageCreator creat = Message.creator(to, from, message);
				creat.create();
				
				optMap.put(passwordResetRequestDto.getUserName(), code);
				passwordResetResponseDto = new PasswordResetResponseDto(OptStatus.DELIVERED, message);
				
				LOGGER.info("Send sms  {} and {}", passwordResetRequestDto, getTwilioConfig().getTrialNumber());
			}catch(Exception ex) {
				passwordResetResponseDto = new PasswordResetResponseDto(OptStatus.FAILED, ex.getMessage());
			}
			return Mono.just(passwordResetResponseDto);
		} else {
			return Mono.error(new IllegalArgumentException("Phone number ["+passwordResetRequestDto.getPhoneNumber()+"] is not a valid number"));
		}
	}

	@Override
	public Mono<String> validateOTP(String userName, String userInputOtp){
		if (userInputOtp.equals(optMap.get(userName))) {
			return Mono.just("Valid OPT please proceed with your transaction");
		} else {
			return Mono.just("Code incorecte ");
		}
	}
	
	private boolean isPhoneNumberValid(String phoneNumber) {
		// TODO: Implement phone number validator
		if (!phoneNumber.equals(null)) {
			if (!phoneNumber.equals((" ").trim())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	// 6 digit otp
	private String generateOTP() {
		return new DecimalFormat("000000")
				.format(new Random().nextInt(999999));
	}


	public TwilioConfig getTwilioConfig() {
		return twilioConfig;
	}



}
