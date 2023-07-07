package com.chat.Service.service;
/**
 * @author ali
 *
 */
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.chat.Service.entity.Roles;

@Service
public interface RolesService {
 
	public Map<String, String> saveRoles(@Valid Roles rolesUser);	
	public Roles findByRolesUser(String roles);

	
	public Map<String, String> addRolesToPersonnes(String email, String roles);

	public List<Roles> fetchRoles();
}
