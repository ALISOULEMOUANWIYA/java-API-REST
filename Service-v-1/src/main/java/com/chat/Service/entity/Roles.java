package com.chat.Service.entity;
/**
 * @author ali
 *
 */
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;


@Table
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Roles{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;

    @Column(length = 100, nullable = false, unique = true)
    private String rolesUser;
    
}