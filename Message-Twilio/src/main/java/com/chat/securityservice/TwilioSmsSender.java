package com.chat.securityservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

@Service("twilio")
public class TwilioSmsSender implements SmsSender{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(TwilioSmsSender.class);
	
	private final TwilioConfiguration twilioConfiguration;
	
	@Autowired
	public TwilioSmsSender(TwilioConfiguration twilioConfiguration) {
		this.twilioConfiguration = twilioConfiguration;
	}

	@Override
	public void sendSms(SmsRequest smsRequest) {
		if (isPhoneNumberValid(smsRequest.getPhoneNumber())) {
			PhoneNumber to = new PhoneNumber(smsRequest.getPhoneNumber());
			PhoneNumber from = new PhoneNumber(getTwilioConfiguration().getTrialNumber());
			String message = smsRequest.getMessage();
			MessageCreator creat = Message.creator(to, from, message);
			creat.create();
			LOGGER.info("Send sms  {} and {}", smsRequest, getTwilioConfiguration().getTrialNumber());
		} else {
			throw new IllegalArgumentException(
			  "Phone number ["+smsRequest.getPhoneNumber()+"] is not a valid number"
			);
		}

		
	}

	private boolean isPhoneNumberValid(String phoneNumber) {
		// TODO: Implement phone number validator
		LOGGER.info("phone Number sms  {}", phoneNumber);
		return true;
	}

	public TwilioConfiguration getTwilioConfiguration() {
		return twilioConfiguration;
	}
	
	
}
