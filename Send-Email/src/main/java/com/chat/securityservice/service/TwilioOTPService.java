package com.chat.securityservice.service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chat.securityservice.config.SendGridConfig;
import com.chat.securityservice.entities.EmailResetRequestDto;
import com.chat.securityservice.entities.EmailResetResponseDto;
import com.chat.securityservice.enumerable.OptStatus;
import com.chat.securityservice.repository.EmailSender;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import reactor.core.publisher.Mono;

@Service("twilio")
public class TwilioOTPService implements EmailSender{
	
	private Map<String, String> optMap = new HashMap<>();
	@Autowired
	private SendGrid sg = null;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(TwilioOTPService.class);
	
	private final SendGridConfig sendGridConfig;
	
	@Autowired
	public TwilioOTPService(SendGridConfig sendGridConfig) throws IOException{
		this.sendGridConfig = sendGridConfig;
		LOGGER.info("SendGrid initialised ... with account sid {} ", sendGridConfig.getApiKey());
	}

	@Override
	public Mono<EmailResetResponseDto> sendEmail(EmailResetRequestDto emailResetRequestDto) {
		EmailResetResponseDto passwordResetResponseDto = null; 
		if (isEmailValid(emailResetRequestDto.getEmail())) {
			Email from = new Email(getSendGridConfig().getEmailUser());
			Email to = new Email(emailResetRequestDto.getEmail());
			String code = generateOTP();
			String subject = emailResetRequestDto.getSubjectEmail();
			String message = "( LID : "+ code +" )";
			Content content = 
					new Content("text/html","And <em>Dear Customer,Your OPT</em> is <Strong> ALI SOULE </Strong> please Use this Passcode to Resset Your password");
			
			// Structur Mail
			Mail creatMail = new Mail(from, subject, to, content);
			
			try {
				Request rq = new Request();
				
				// Methode Mail Post
				rq.setMethod(Method.POST);
				// The methode would like send message
				rq.setEndpoint("mail/send");
				// Build Mail
				rq.setBody(creatMail.build());
				
				Response res = this.sg.api(rq);
				System.out.println(res.getStatusCode());
				System.out.println(res.getHeaders());
				System.out.println(res.getBody());
				
				optMap.put(emailResetRequestDto.getEmail(), code);
				passwordResetResponseDto = new EmailResetResponseDto(OptStatus.DELIVERED, message);
				
				LOGGER.info("Email:  {} and {}", emailResetRequestDto);
			}catch(Exception ex) {
				passwordResetResponseDto = new EmailResetResponseDto(OptStatus.FAILED, ex.getMessage());
			}
			return Mono.just(passwordResetResponseDto);
		} else {
			passwordResetResponseDto = new EmailResetResponseDto(OptStatus.FAILED, "Email: ["+emailResetRequestDto.getEmail()+"] is not valid");
			return Mono.just(passwordResetResponseDto);
		}
	}

	@Override
	public Mono<String> validateOTP(String userEmail, String userInputOtp){
		if (userInputOtp.equals(optMap.get(userEmail))) {
			return Mono.just("Valid OPT please proceed with your transaction");
		} else {
			return Mono.just("Code incorecte ");
		}
	}
	
	private boolean isEmailValid(String email) {
		// TODO: Implement Email validator

		String FormEmail1 = "@gmail.com";
		String FormEmail2 = "@yahoo.com";
		boolean validatorNameEmail = false;
	    Matcher filter = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(email);
	      while (filter.find()) 
	      {
	  		if ((filter.group()).endsWith(FormEmail1) || (filter.group()).endsWith(FormEmail2)) {
				if ((filter.group()).endsWith(FormEmail1)) {
					String NameEmail = (filter.group()).substring(0, (filter.group()).indexOf(FormEmail1));
					validatorNameEmail = this.validator(NameEmail);
				} else if ((filter.group()).endsWith(FormEmail2)){
					String NameEmail = (filter.group()).substring(0, (filter.group()).indexOf(FormEmail2));
					validatorNameEmail = this.validator(NameEmail);
				}
			} else {
				validatorNameEmail = false;
			}
	      }
	      return validatorNameEmail;
	}
	
	private boolean validator(String value) {
		boolean validatorNameEmail = false;
		if (value.length() > 3) {
			if (!(value.trim()).equals("") ) {
				validatorNameEmail = this.getTypeValue(value);
			} else {
				validatorNameEmail = false;
			}
		} else {
			validatorNameEmail = false;
		}
		return validatorNameEmail;
	}
	
	private boolean getTypeValue(String value) {
		if (value == null) {
			return false;
		} else {
			try {
				double d = Double.parseDouble(value);
			} catch (NumberFormatException e) {
				return true;
			}
			return false;
		}
	}
	
	// 6 digit otp
	private String generateOTP() {
		return new DecimalFormat("000000")
				.format(new Random().nextInt(999999));
	}


	public SendGridConfig getSendGridConfig() {
		return sendGridConfig;
	}



}
