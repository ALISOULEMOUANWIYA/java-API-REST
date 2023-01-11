package com.example.projetspringa.controller;

import java.util.List;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.projetspringa.entity.Departement;
import com.example.projetspringa.error.DepartementNotFoundException;
import com.example.projetspringa.service.DepartementService;




@RestController
public class DepartementController {
	
	@Autowired
	private DepartementService departementsService;
	
	private final Logger log = LoggerFactory.getLogger(DepartementController.class);
	
	/*@Value("${Some.config}")
	private String some;*/
	
	
	
	
	@PostMapping("/save")
	public Departement saveDepartement(@Valid @RequestBody Departement departement) {
		log.info("Inside saveDepartement of DepartementController");
		return departementsService.saveDepartement(departement);
	}
	
	@GetMapping("/loginbak")
	public String login() {
		System.out.println("Inside fetchDepartementById of DepartementController");
		return "ok";
	}
	
	
	@PostMapping("/savesListe")
	public List<Departement> saveDepartementListe(@Valid @RequestBody List<Departement> departement) {
		//log.info("Inside saveDepartementListe of DepartementController");
		return departementsService.saveDepartementListe(departement);
	}
	
	@GetMapping("/getListe")
	public List<Departement> fetchDepartement(){
		log.info("Inside fetchDepartement of DepartementControlleer");
		return departementsService.fetchDepartement();
	}
	
	@GetMapping("/Searche/{nom}")
	public List<Departement> fetchDepartementByName(@PathVariable("nom") String nom){
		log.info("Inside fetchDepartementById of DepartementController");
		return departementsService.fetchDepartementByName(nom);
	}
	
	@GetMapping("/getOne/{id}")
	public Departement fetchDepartementById(@PathVariable("id") Long id) throws DepartementNotFoundException {
		log.info("Inside fetchDepartementById of DepartementController");
		return departementsService.fetchDepartementById(id);
	}
	
	
	@DeleteMapping("/deleteOne/{id}")
	public String deleteDepartementById(@PathVariable("id") Long id){
		log.info("Inside deleteDepartementById of DepartementController");
		return departementsService.deleteDepartementById(id);
	}
	
	/*@DeleteMapping("/deleteO/{id}")
	public String deleteDepartementById(@PathVariable("id") Long id){
		return departementsService.deleteDepartementById(id);
	}*/
	
	@PutMapping("/update/{id}")
	public Departement updateDepartement(@PathVariable("id") Long id, @RequestBody Departement departement) {
		log.info("Inside updateDepartement of DepartementController");
		return departementsService.updateDepartement(id, departement);
	}
}


