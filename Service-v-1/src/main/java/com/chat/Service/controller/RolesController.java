package com.chat.Service.controller;
/**
 * @author ali
 *
 */

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chat.Service.entity.Roles;
import com.chat.Service.entity.RolesUser;
import com.chat.Service.service.RolesService;




@RestController("/api/v1/roles")
public class RolesController {
	

	@Autowired
	private RolesService rolesService;
	
	private final Logger log = 
			LoggerFactory.getLogger(RolesController.class);
	
	
	
	@PostMapping(path = "/addroles")
	@PostAuthorize("hasAuthority('admin')")
	public Map<String, String> saveRoles(@Valid @RequestBody Roles  rolesUser) {
		return rolesService.saveRoles(rolesUser);
	}
	
	@PostMapping(path = "/addRoleUser")
	@PostAuthorize("hasAuthority('secretaire')")
	public Map<String, String> addRolesPersonnes(@Valid @RequestBody RolesUser  rolesUserForm) {
		 return rolesService.addRolesToPersonnes(rolesUserForm.getUsername(), rolesUserForm.getRolesName());
	}
	
	@GetMapping(path = "/ListeRoles")
	@PostAuthorize("hasAuthority('secretaire') or hasAuthority('admin')")
	public List<Roles> fetchRoles(){
		log.info("Inside fetchRoles of RolesController");
		return rolesService.fetchRoles();
	}
	
}
