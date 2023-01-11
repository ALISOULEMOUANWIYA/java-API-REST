package com.chat.securityservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@org.springframework.stereotype.Service
public class Service {
	private final SmsSender SmsSender;
	
	@Autowired
	public Service(@Qualifier("twilio") TwilioSmsSender SmsSender) {
		this.SmsSender =  SmsSender;
	}
	
	public void sendSms(SmsRequest smsRequiRequest) {
		getSmsSender().sendSms(smsRequiRequest);
	}

	public SmsSender getSmsSender() {
		return SmsSender;
	}
}
