package com.example.projetspringa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Departement {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long departementId;
	
	//@NotBlank(message = "Please add Departement Name")
	@Column(name ="departementName")
    private String departementName;
	
	@Column(name ="departementAddress")
    private String departementAddress;
	
	@Column(name ="departementCode")
    private String departementCode;

}
