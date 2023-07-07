package com.chat.Service;
/**
 * @author ali
 *
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// Information de commentaire :
//----ceci est pour l'ajout directe des donnees à fin d'aller plus vite 
//----mais apres le premier debuigue, il est crussialle de les commentes a nouveau 
//----pour eviter la duplication des donnes

   
import java.util.ArrayList;
import org.springframework.boot.CommandLineRunner;
import com.chat.Service.entity.Roles;
import com.chat.Service.entity.UsersE;
import com.chat.Service.service.RolesService;
import com.chat.Service.service.UsersEService;




@SpringBootApplication
public class ServiceV1Application {

	public static void main(String[] args) {
		SpringApplication.run(ServiceV1Application.class, args);
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	} 


	@Bean
	CommandLineRunner start(UsersEService userS, RolesService rolesS) {
		return args ->{
			/*userS.saveUser(new UsersE(null,
					2836237L,
					"ALI SOULE",        
				    "Mouanwiya",
					false,
				    "ali@gmail.com",  
				    "passer123",  
				    new ArrayList<>()
			));
			userS.saveUser(new UsersE(null,
					182738L, 
					"SOULE",        
				    "Moussa",
					false,
				    "moussa@gmail.com",  
				    "passer123",
				    new ArrayList<>()
			));*/
			/*rolesS.saveRoles(new Roles(null, "user"));
			rolesS.saveRoles(new Roles(null, "admin"));
			rolesS.saveRoles(new Roles(null, "secretaire"));
			rolesS.saveRoles(new Roles(null, "surveillant"));*/
			
			/*rolesS.addRolesToPersonnes("ali@gmail.com", "user");
			rolesS.addRolesToPersonnes("ali@gmail.com", "admin");
			rolesS.addRolesToPersonnes("moussa@gmail.com", "admin");
			rolesS.addRolesToPersonnes("moussa@gmail.com", "secretaire");*/
		};
	} 

}
