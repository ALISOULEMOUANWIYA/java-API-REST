package com.chat.Service.config;
/**
 * @author ali
 *
 */
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.mail")
public class EmailConfig {
	private String username;
	private String host;
	private String port;
}
