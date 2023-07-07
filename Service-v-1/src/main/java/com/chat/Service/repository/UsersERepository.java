package com.chat.Service.repository;
/**
 * @author ali
 *
 */
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chat.Service.entity.UsersE;
import com.chat.Service.error.UsersNotFoundException;


@Repository
public interface UsersERepository extends JpaRepository<UsersE, Long>{

	public List<UsersE> findByNameIgnoreCase(String name) throws UsersNotFoundException;
	
	public List<UsersE> findByEmailIgnoreCase(String email);
	
	//@Query("SELECT u FROM userse u WHERE u.email = :em")
	public UsersE findByEmail(/*@Param("em")*/ String email);

    public UsersE findByPhone(String value);

	public UsersE findByName(String nom);

}
