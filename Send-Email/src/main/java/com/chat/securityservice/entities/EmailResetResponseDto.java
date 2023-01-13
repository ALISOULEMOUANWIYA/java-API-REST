package com.chat.securityservice.entities;


import com.chat.securityservice.enumerable.OptStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailResetResponseDto {

	private OptStatus status;
	private String message;
	

}
