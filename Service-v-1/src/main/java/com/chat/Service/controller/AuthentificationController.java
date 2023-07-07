package com.chat.Service.controller;
/**
 * @author ali
 *
 */
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.Service.entity.UsersE;
import com.chat.Service.service.UsersEService;



@RestController
@RequestMapping("api/v1/auth")
public class AuthentificationController {
	
	//@Autowired
	//private UsersEService usr;

	/* 
	@PostMapping("/Register")
	public UsersE RegisterUser(@Valid @RequestBody UsersE  userC, HttpServletResponse response) {
		return usr.saveUser(userC, response);
	}
	*/
}
