package com.chat.Service.service;
/**
 * @author ali
 *
 */
import java.util.List;

import javax.validation.Valid;

import com.chat.Service.entity.UserSertified;
import com.chat.Service.error.UsersNotFoundException;


public interface UsersSertifiedService {

	public void saveUser(UserSertified userC) ;

	public UserSertified fetchUserById(Long id) throws UsersNotFoundException;

	public String deleteUserById(Long id);

	public List<UserSertified> fetchUserByName(String fname);
	
	public List<UserSertified> fetchUserByEmail(String email);
	
	public UserSertified findByEmail(String email);

	public List<UserSertified> fetchUserE();

	public List<UserSertified> saveUserListe(@Valid List<UserSertified> userC);

	public UserSertified updateUser(Long id, UserSertified userC);

	public UserSertified findByName(String nom);
}
