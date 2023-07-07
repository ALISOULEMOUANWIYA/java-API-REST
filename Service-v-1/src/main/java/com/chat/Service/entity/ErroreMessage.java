package com.chat.Service.entity;
/**
 * @author ali
 *
 */

import org.springframework.http.HttpStatus;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErroreMessage {
	private HttpStatus status;
	private String message;
}
