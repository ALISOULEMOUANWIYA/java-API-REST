package com.chat.Service.repository;
/**
 * @author ali
 *
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chat.Service.entity.Roles;

@Repository
public interface RolesRepository  extends JpaRepository<Roles, Long>{
	public Roles findByRolesUser(String roles);	

}
