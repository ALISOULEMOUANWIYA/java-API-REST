package com.chat.Service.service;
/**
 * @author ali
 *
 */
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chat.Service.entity.UserSertified;
import com.chat.Service.entity.UsersE;
import com.chat.Service.error.UsersNotFoundException;
import com.chat.Service.repository.UsersERepository;
import com.chat.Service.security.SECURITY_UTILS;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional
public class UsersEServiceImpl implements UsersEService
{	
	@Autowired
	private UsersERepository usersERepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UsersSertifiedService usersSertifiedService;
	
	
	@Override
	public void saveUser(UsersE userC, HttpServletResponse response) throws StreamWriteException, DatabindException, IOException {
		if (
			usersERepository.findByEmail(userC.getEmail()) != null 
		) {
			Map<String, String> massage = new HashMap<>();
			massage.put("Message-Error", "Email Is ready exist");
			response.setContentType("application/json");
			new ObjectMapper().writeValue(response.getOutputStream(), massage);
		} else{
			if (userC.getPhone().length() == 9 && usersERepository.findByPhone(SECURITY_UTILS.typeSenegale(userC.getPhone())) == null ) {
				userC.setPhone(SECURITY_UTILS.typeSenegale(userC.getPhone()));
				message(userC, response);
			}
			 else if (userC.getPhone().length() == 7 && usersERepository.findByPhone(SECURITY_UTILS.typeComorers(userC.getPhone())) == null ) {
				userC.setPhone(SECURITY_UTILS.typeComorers(userC.getPhone()));
				message(userC, response);
			}else{
				Map<String, String> massage = new HashMap<>();
				massage.put("Message-Error", "Phone Number Is ready exist");
				response.setContentType("application/json");
				new ObjectMapper().writeValue(response.getOutputStream(), massage);
			}
		}

	}

	private void message(UsersE userC, HttpServletResponse response) throws StreamWriteException, DatabindException, IOException{
		String pw = userC.getPassword();
		userC.setPassword(passwordEncoder.encode(pw));
		usersERepository.save(userC);
		Map<String, String> massage = new HashMap<>();
		massage.put("Message-Success", "Add User longueur telephone : "+userC.getPhone().length());
		response.setContentType("application/json");
		new ObjectMapper().writeValue(response.getOutputStream(), massage);
	}

/* 
	@Override
	public void saveUser(UsersE userC) {
		
			String pw = userC.getPassword();
			userC.setPassword(passwordEncoder.encode(pw));
			usersERepository.save(userC);
	}
*/
	@Override
	public String deleteUserById(Long id) {
		usersERepository.deleteById(id);
		 return "User"+id+" is successfully deleted";
	} 

	@Override
	public UsersE fetchUserById(Long id) throws UsersNotFoundException{
		Optional<UsersE> users = 
				usersERepository.findById(id);
		
		if (!users.isPresent()) {
			throw new UsersNotFoundException("User Not Available");
		}
		
		return users.get();
	}

	@Override
	public List<UsersE> fetchUserByName(String name) throws UsersNotFoundException{
		return usersERepository.findByNameIgnoreCase(name);
	}

	@Override
	public List<UsersE> fetchUserE() {
		return usersERepository.findAll();
	}

	@Override
	public List<UsersE> saveUserListe(@Valid List<UsersE> userC) {
		return usersERepository.saveAll(userC);
	}

	@SuppressWarnings("static-access")
	@Override
	public UsersE updateUser(Long id, UsersE userC) {
		UsersE pr = usersERepository.findById(id).get();
		
		if 
		(Objects.nonNull(userC.getPhone())) 
		{
			if (userC.getPhone().length() == 8) {
				pr.setPhone(SECURITY_UTILS.typeSenegale(userC.getPhone()));
			}
			if (userC.getPhone().length() == 6) {
				pr.setPhone(SECURITY_UTILS.typeComorers(userC.getPhone()));
			}
		} 
		
		if (Objects.nonNull(userC.getName()) && 
				!"".equalsIgnoreCase(userC.getName())) {
			pr.setName(userC.getName());
		} 
		
		if (Objects.nonNull(userC.getPrenom()) && 
				!"".equalsIgnoreCase(userC.getPrenom())) {
			pr.setPrenom(userC.getPrenom());
		} 
		
		if (Objects.nonNull(userC.getEmail()) && 
				!"".equalsIgnoreCase(userC.getEmail())) {
			pr.setEmail(userC.getEmail());
		}
		
		if (Objects.nonNull(userC.getPassword()) && 
				!"".equalsIgnoreCase(userC.getPassword())) {
			pr.setPassword(userC.getPassword());
		}

		if (Objects.nonNull(userC.getSertified())) {
			pr.setSertified(userC.getSertified());
		}

		UserSertified uSert =  usersSertifiedService.findByEmail(userC.getEmail());
		UserSertified uss = new UserSertified(null, uSert.getDateTimeSertified() , userC.getEmail(), userC.getName()); 
		usersSertifiedService.updateUser(uSert.getId(), uss);
		
		return usersERepository.save(pr);
	}
	
	@Override
	public UsersE findByName(String nom) {
		return usersERepository.findByName(nom);
	}

	@Override
	public UsersE findByEmail(String email) {
		return usersERepository.findByEmail(email);
	}

	@Override
	public List<UsersE> fetchUserByEmail(String email) {
		return usersERepository.findByEmailIgnoreCase(email);
	}


	@Override
	public UsersE findByPhone(String value) {
		return usersERepository.findByPhone(value);
	}


}
