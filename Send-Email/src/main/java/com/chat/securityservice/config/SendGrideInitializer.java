package com.chat.securityservice.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sendgrid.SendGrid;


@Configuration
public class SendGrideInitializer {

	private final static Logger LOGGER = LoggerFactory.getLogger(SendGrideInitializer.class);
	private final SendGridConfig sendGridConfig;
	
	@Autowired
	public SendGrideInitializer(SendGridConfig sendGridConfig) throws IOException{
		this.sendGridConfig = sendGridConfig;
		
		//SendGrid sg = new SendGrid(System.getenv(sendGridConfig.getApiKey()));
		LOGGER.info("Twilio initialised ... with account sid {} ", sendGridConfig.getApiKey());
	}
	
	@Bean
	public SendGrid getSendGride() {
		return new SendGrid(sendGridConfig.getApiKey());
	}
}
