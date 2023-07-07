package com.chat.Service.error;
/**
 * @author ali
 *
 */
import java.time.ZonedDateTime;


import org.springframework.http.HttpStatus;



public class ApiException {
	
	private final String message;
	private final HttpStatus httpStatus;
	private final ZonedDateTime temestamp;
	
	
	public ApiException(String message, 
			HttpStatus httpStatus, 
			ZonedDateTime temestamp) {
		super();
		this.message = message;
		this.httpStatus = httpStatus;
		this.temestamp = temestamp;
	}
	
	
	public String getMessage() {
		return message;
	}



	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public ZonedDateTime getTemestamp() {
		return temestamp;
	}

	
	
	
	
	
	
}
