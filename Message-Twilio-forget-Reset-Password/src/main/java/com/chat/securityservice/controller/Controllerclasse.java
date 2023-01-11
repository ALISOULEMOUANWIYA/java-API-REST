package com.chat.securityservice.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.securityservice.entities.PasswordResetRequestDto;
import com.chat.securityservice.entities.PasswordResetResponseDto;
import com.chat.securityservice.service.Service;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/")
public class Controllerclasse {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(Controllerclasse.class);

	@Autowired
	private Service service; 
	
	@PostMapping(path = "/sendOTP")
	public Mono<PasswordResetResponseDto> sendOTP(@Valid @RequestBody PasswordResetRequestDto passwordResetRequestDto){
		System.out.println(passwordResetRequestDto);
		LOGGER.info("Classe Controller ( Phone : {},  User NAme : {}, code : {} )", 
				passwordResetRequestDto.getPhoneNumber(), 
				passwordResetRequestDto.getUserName(), 
				passwordResetRequestDto.getOneTimePassword()
				);
		return service.sendSms(passwordResetRequestDto);
	}
	
	@PostMapping(path = "/validateOTP")
	public Mono<String> validateOTP(@Valid @RequestBody PasswordResetRequestDto passwordResetRequestDto){

		
		return service.validateOTP(passwordResetRequestDto.getUserName(), passwordResetRequestDto.getOneTimePassword());
	}
}
