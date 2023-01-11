package com.example.projetspringa.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.projetspringa.entity.Departement;
import com.example.projetspringa.service.DepartementService;


@WebMvcTest(DepartementController.class)
class DepartementControllerTest {
	
	@Autowired private MockMvc mockMvc;
	
	@MockBean private DepartementService departementService;
	
	private Departement departement;

	@BeforeEach
	void setUp() throws Exception {
		 departement =
				Departement.builder()
				.departementName("Volovolo")
				.departementAddress("Moroni")
				.departementCode("VO-84")
				.departementId(1L)
				.build();
	}

	@Test
	void testSaveDepartement() throws Exception {
		Departement savedepartement =
				Departement.builder()
				.departementName("VO")
				.departementAddress("Comores")
				.departementCode("VO-84")
				.build();
		
		Mockito.when(departementService.saveDepartement(savedepartement))
		.thenReturn(departement);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/save")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n"
						+ "    \"departementName\": \"VO\",\n"
						+ "    \"departementAddress\": \"Comores\",\n"
						+ "    \"departementCode\": \"VO-84\"\n"
						+ "}"))
		.andExpect(MockMvcResultMatchers.status().isOk());
		
	}

	@Test
	void testFetchDepartementByIdLong() throws Exception  {
		Mockito.when(departementService.fetchDepartementById(1L))
		.thenReturn(departement);
		
		mockMvc.perform(get("/getOne/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.departementName").value(departement.getDepartementName()));
	}

}
