package com.chat.Service.error;
/**
 * @author ali
 *
 */
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.chat.Service.entity.ErroreMessage;




@ControllerAdvice
@ResponseStatus
public class RestResponseEntityExceptionHandler 
		extends ResponseEntityExceptionHandler{
	
	
	@ExceptionHandler(UsersNotFoundException.class)
	public ResponseEntity<ErroreMessage> departementNotFoundException(UsersNotFoundException exception
			, WebRequest request) {
		
		ErroreMessage message = 
				new ErroreMessage(
						HttpStatus.NOT_FOUND, 
						exception.getMessage()
						);
		
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(message);
	}
}
