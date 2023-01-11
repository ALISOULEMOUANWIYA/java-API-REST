package com.example.projetspringa.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.projetspringa.entity.Departement;


@DataJpaTest
class DepartementRepositoryTest {
	
	@Autowired
	private DepartementRepository departR;
	
	@Autowired
	private TestEntityManager entityManager;

	@BeforeEach
	void setUp() throws Exception {
		Departement departement =
				Departement.builder()
				.departementName("Mbeni")
				.departementAddress("Saga")
				.departementCode("MO-84")
				.build();
		
		entityManager.persist(departement);
	}

	@Test
	@DisplayName("Get Data based on valida Departement Name")
	public void whenFindById_ThenReturnDepartement() {
		Departement found = departR.findById(1L).get();
		
		assertEquals(found.getDepartementName(), "Mbeni");
	}
}
