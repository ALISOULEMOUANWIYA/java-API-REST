package com.chat.Service.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.chat.Service.entity.UsersE;
import com.chat.Service.error.UsersNotFoundException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;

/**
 * @author ali
 *
 */
public interface UsersEService {

	//public void saveUser(UsersE userC) ;
	public void saveUser(UsersE userC, HttpServletResponse response) 
			throws StreamWriteException, DatabindException, IOException ;

	public UsersE fetchUserById(Long id) throws UsersNotFoundException;

	public String deleteUserById(Long id);

	public List<UsersE> fetchUserByName(String fname) throws UsersNotFoundException;
	
	public List<UsersE> fetchUserByEmail(String email);
	
	public UsersE findByEmail(String email);

	public List<UsersE> fetchUserE();

	public List<UsersE> saveUserListe(@Valid List<UsersE> userC);

	public UsersE updateUser(Long id, UsersE userC);

	public UsersE findByPhone(String value);
    public UsersE findByName(String username);
}
