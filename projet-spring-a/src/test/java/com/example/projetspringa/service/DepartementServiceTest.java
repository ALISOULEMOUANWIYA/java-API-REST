package com.example.projetspringa.service;


import static org.junit.Assert.assertEquals;

//import static org.junit.Assert.assertEquals;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.projetspringa.entity.Departement;
import com.example.projetspringa.repository.DepartementRepository;



@SpringBootTest
class DepartementServiceTest {
	
	@MockBean
	private DepartementRepository departementRepository;
	
	@Autowired
	private DepartementService dep;
	
	
	@BeforeEach
	void setUp() throws Exception {
		Departement departement =
				Departement.builder()
				.departementName("IT")
				.departementAddress("Moroni")
				.departementCode("MO-84")
				.departementId(1L)
				.build();
		
		Mockito.when(departementRepository.findByDepartementNameIgnoreCase("IT"))
		.thenReturn(departement);
	}
	
	@Test
	@DisplayName("Get Data based on valida Departement Name")
	public void whenValideDepartementName_ThenDepartementShouldFound() {
		String dept = "IT";
		Departement found = dep.findDepartementByName(dept);
		
		assertEquals(dept, found.getDepartementName());
	}
}
