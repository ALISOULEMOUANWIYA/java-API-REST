package com.chat.Service.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.Service.entity.UserSertified;
import com.chat.Service.error.UsersNotFoundException;
import com.chat.Service.service.UsersSertifiedService;


@RestController
@RequestMapping("api/v1/userSertified")
public class UserSertifiedController {

    private UsersSertifiedService UserSertifiedService;
	
	private final Logger log = 
			LoggerFactory.getLogger(UsersController.class);

	@Autowired
	public UserSertifiedController(
		UsersSertifiedService UserSertifiedService
	){
		this.UserSertifiedService  = UserSertifiedService;
	}
 
    	@GetMapping("/getOne/{id}")
	@PostAuthorize("hasAuthority('admin')")
	public UserSertified fetchUserById(@PathVariable("id") Long id) throws UsersNotFoundException {
		log.info("Inside fetch User of User");
		return UserSertifiedService.fetchUserById(id);
	}
	
	
	@DeleteMapping("/deleteOne/{id}")
	@PostAuthorize("hasAuthority('admin') or hasAuthority('secretaire')")
	public String deleteUserById(@PathVariable("id") Long id){
		log.info("Inside delete User of User");
		return UserSertifiedService.deleteUserById(id);
	}
	
	@PutMapping("/update/{id}")
	@PostAuthorize("hasAuthority('admin') or hasAuthority('secretaire')")
	public UserSertified updateUser(@PathVariable("id") Long id, @RequestBody UserSertified userC) {
		log.info("Inside update Userof User");
		return UserSertifiedService.updateUser(id, userC);
	}
	


	@GetMapping(path = "/profile")
	@PostAuthorize("hasAuthority('user')")
	public UserSertified prifile(Principal principal) {
		return UserSertifiedService.findByName(principal.getName());
	}
	
	//--------------------------------------------------------------------
}
