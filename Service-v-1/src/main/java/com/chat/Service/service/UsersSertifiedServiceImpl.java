package com.chat.Service.service;
/**
 * @author ali
 *
 */

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chat.Service.entity.UserSertified;
import com.chat.Service.error.UsersNotFoundException;
import com.chat.Service.repository.UserSertifiedRepository;

@Service
@Transactional
public class UsersSertifiedServiceImpl implements UsersSertifiedService
{	
	@Autowired
	private UserSertifiedRepository userSertifiedRepo;


	@Override
	public void saveUser(UserSertified userC) {
			userSertifiedRepo.save(userC);
	}

	@Override
	public String deleteUserById(Long id) {
		userSertifiedRepo.deleteById(id);
		 return "User"+id+" is successfully deleted";
	} 

	@Override
	public UserSertified fetchUserById(Long id) throws UsersNotFoundException{
		Optional<UserSertified> users = 
				userSertifiedRepo.findById(id);
		
		if (!users.isPresent()) {
			throw new UsersNotFoundException("User Not Available");
		}
		
		return users.get();
	}

	@Override
	public List<UserSertified> fetchUserByName(String name) {
		return userSertifiedRepo.findByNameIgnoreCase(name);
	}

	@Override
	public List<UserSertified> fetchUserE() {
		return userSertifiedRepo.findAll();
	}

	@Override
	public List<UserSertified> saveUserListe(@Valid List<UserSertified> userC) {
		return userSertifiedRepo.saveAll(userC);
	}

	@SuppressWarnings("static-access")
	@Override
	public UserSertified updateUser(Long id, UserSertified userC) {
		UserSertified pr = userSertifiedRepo.findById(id).get();

		
		if (Objects.nonNull(userC.getName()) && 
				!"".equalsIgnoreCase(userC.getName())) {
			pr.setName(userC.getName());
		} 

		
		if (Objects.nonNull(userC.getEmail()) && 
				!"".equalsIgnoreCase(userC.getEmail())) {
			pr.setEmail(userC.getEmail());
		}


		return userSertifiedRepo.save(pr);
	}
	

	@Override
	public UserSertified findByEmail(String email) {
		return userSertifiedRepo.findByEmail(email);
	}

	@Override
	public List<UserSertified> fetchUserByEmail(String email) {
		return userSertifiedRepo.findByEmailIgnoreCase(email);
	}

	@Override
	public UserSertified findByName(String nom) {
		return userSertifiedRepo.findByName(nom);
	}
}
