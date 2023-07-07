package com.chat.Service.repository;
/**
 * @author ali
 *
 */
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chat.Service.entity.UserSertified;



@Repository
public interface UserSertifiedRepository extends JpaRepository<UserSertified, Long>{

	public List<UserSertified> findByNameIgnoreCase(String name);
	
	public List<UserSertified> findByEmailIgnoreCase(String email);
	
	//@Query("SELECT u FROM userse u WHERE u.email = :em")
	public UserSertified findByEmail(/*@Param("em")*/ String email);

	public UserSertified findByName(String nom);

}
