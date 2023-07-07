package com.chat.Service.service;
/**
 * @author ali
 *
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chat.Service.entity.Roles;
import com.chat.Service.entity.UsersE;
import com.chat.Service.repository.RolesRepository;
import com.chat.Service.repository.UsersERepository;




@Service
@Transactional
public class RolesServiceImpl implements RolesService{
	
	@Autowired
	private RolesRepository rolesRepository;
	
	@Autowired
	private UsersERepository UserRepository;
	
	@Override
	public Map<String, String> saveRoles(@Valid Roles rolesUser) {
		Map<String, String> message = new HashMap<>();
		if (rolesRepository.findByRolesUser(rolesUser.getRolesUser()) != null) {
			message.put("Message", "Roles is ready");
			return message;
		}
		message.put("Message", "Roles add succesfuly");
		rolesRepository.save(rolesUser);
		return message;
	}

	@Override
	public Roles findByRolesUser(String roles){
		return rolesRepository.findByRolesUser(roles) ;
	}


	@Override
	public List<Roles> fetchRoles() {
		return rolesRepository.findAll();
	}


	@Override
	public Map<String, String> addRolesToPersonnes(String email, String roles) {
		Map<String, String> message = new HashMap<>();
		List<String> cars = new ArrayList<String>();
		Roles role = rolesRepository.findByRolesUser(roles);
		UsersE us = UserRepository.findByEmail(email);
		cars = us.getRoles().stream().map(r ->
							r.getRolesUser()
						).collect(Collectors.toList());
						
		us.getRoles().add(role);
		message.put("Message", "Roles User add succesfuly");
		return message;
	}





}
