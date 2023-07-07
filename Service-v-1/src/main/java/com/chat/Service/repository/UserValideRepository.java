package com.chat.Service.repository;
/**
 * @author ali
 *
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chat.Service.entity.UserValide;

@Repository
public interface UserValideRepository  extends JpaRepository<UserValide, Long>{
	public UserValide findByEmail(String email);

}
