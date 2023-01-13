package com.chat.securityservice.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.securityservice.entities.EmailResetRequestDto;
import com.chat.securityservice.entities.EmailResetResponseDto;
import com.chat.securityservice.service.Service;
import com.fasterxml.jackson.annotation.JsonProperty;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/")
public class Controllerclasse {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(Controllerclasse.class);

	@Autowired
	private Service service; 
	
	@PostMapping(path = "/sendEmail")
	public Mono<EmailResetResponseDto> sendOTP(@Valid @RequestBody EmailResetRequestDto emailResetRequestDto){
		LOGGER.info("Classe Controller ( Email : {} )", 
				emailResetRequestDto.getEmail()
				);
		return service.sendEmail(emailResetRequestDto);
	}
	
	@PostMapping(path = "/validateEmail")
	public Mono<String> validateOTP(@Valid @RequestBody EmailResetRequestDto passwordResetRequestDto){

		
		return service.validateOTP(passwordResetRequestDto.getEmail(), passwordResetRequestDto.getOneTimePassword());
	}
}
