package com.chat.Service.service;
/**
 * @author ali
 *
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.chat.Service.entity.UsersE;


@Service
@Transactional
public class UserDatailsServiceImpl implements UserDetailsService {
		private final Logger log = 
			LoggerFactory.getLogger(UserDatailsServiceImpl.class);
	@Autowired
	private UsersEService UserS;
	private ArrayList<String> tableEmail = new ArrayList<String>();	
	@Override
    public UserDetails loadUserByUsername(String value) throws UsernameNotFoundException {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
    	if (!value.equals(null)) {
    		if (this.isEmailValid(value)) {
                return this.autority(UserS.findByEmail(value),  authorities);
			}else if(this.validator(this.typeSenegale(value)) ){
				log.info("User {}", UserS.findByPhone(this.typeSenegale(value)));
                return this.autority(UserS.findByPhone(this.typeSenegale(value)),  authorities);
			}
			else if (this.validator(this.typeComorers(value))) {
				return this.autority(UserS.findByPhone(this.typeComorers(value)),  authorities);
			}
		} 
    	return new User("kd", "hs", authorities);
    }
	
	private User autority(UsersE user, Collection<GrantedAuthority> authorities) {
        if (user != null) {
        	user.getRoles().forEach(r -> {
                authorities.add(new SimpleGrantedAuthority(r.getRolesUser()));
            });
            return new User(user.getName(), user.getPassword(), authorities);
        } 
        return new User("kd", "hs", authorities);
	}
	
	
	private boolean isEmailValid(String email) {
		/*String FormEmail1 = "@gmail.com";
		String FormEmail2 = "@yahoo.com";*/
		tableEmail.add("@gmail.com");
		tableEmail.add("@yahoo.com");
		boolean validatorNameEmail = false;
	    Matcher filter = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(email);
	      while (filter.find()) 
	      {
			for (String emailT : tableEmail) {
				if ((filter.group()).endsWith(emailT)) {
					String NameEmail = (filter.group()).substring(0, (filter.group()).indexOf(emailT));
					validatorNameEmail = this.validator(NameEmail);
				}

				if ((filter.group()).endsWith(emailT)){
					String NameEmail = (filter.group()).substring(0, (filter.group()).indexOf(emailT));
					validatorNameEmail = this.validator(NameEmail);
				}
			}
	  		/*if ((filter.group()).endsWith(FormEmail1) || (filter.group()).endsWith(FormEmail2)) {
				if ((filter.group()).endsWith(FormEmail1)) {
					String NameEmail = (filter.group()).substring(0, (filter.group()).indexOf(FormEmail1));
					validatorNameEmail = this.validator(NameEmail);
				} else if ((filter.group()).endsWith(FormEmail2)){
					String NameEmail = (filter.group()).substring(0, (filter.group()).indexOf(FormEmail2));
					validatorNameEmail = this.validator(NameEmail);
				}
			} */
	      }
	      return validatorNameEmail;
	}
/*	
	private boolean IsNameValide(String value) {
		boolean validatorName = false;
		if (value.length() > 3) 
			if (!(value.trim()).equals("") ) 
				validatorName = this.getTypeValue(value);
			
		 
		return validatorName;
	}
*/	
	private boolean validator(String value) {
		boolean validatorNameEmail = false;
		if (value.length() > 3) 
			if (!(value.trim()).equals("") ) 
				validatorNameEmail = this.getTypeValue(value);
			
		 
		return validatorNameEmail;
	}
	
	private boolean getTypeValue(String value) {
		if (value == null) {
			return false;
		} else {
			try {
				 Double.parseDouble(value);
				 log.info("Is Number {}", false);
			} catch (NumberFormatException e) {
				log.info("is String {}", true);
				return true;
			}
			return false;
		}
	}
	
    private String typeSenegale(String phS){
        String phone = "";
		for(int i = 0; i < phS.length(); i ++){
			if(i == 2){
				phone += "-"+String.valueOf(phS.charAt(i));
			}
			else if(i == 5){
				phone += "-"+String.valueOf(phS.charAt(i));
			}
			else if(i == 7){
				phone += "-"+String.valueOf(phS.charAt(i));
			}else{
				phone += String.valueOf(phS.charAt(i));
			}
		}
        return phone;
    }

    private String typeComorers(String phC){
        String phone = "";
		for(int i = 0; i < phC.length(); i ++){
			if(i == 3 ){
				phone += "-"+String.valueOf(phC.charAt(i));
			}
            else if(i == 5 ){
				phone += "-"+String.valueOf(phC.charAt(i));
			}else{
				phone += String.valueOf(phC.charAt(i));
			}
		}
        return phone;
    }

}
