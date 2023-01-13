package com.chat.securityservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "twilio")
public class SendGridConfig {
	private String apiKey;
	private String emailUser;
	private String accountSid;
	private String authToken;
	private String trialNumber;
}
