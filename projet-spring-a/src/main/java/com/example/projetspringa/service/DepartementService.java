package com.example.projetspringa.service;

import java.util.List;

import com.example.projetspringa.entity.Departement;
import com.example.projetspringa.error.DepartementNotFoundException;

public interface DepartementService {

	
	
	public Departement saveDepartement(Departement departement);
	
	public Departement fetchDepartementById(Long id) throws DepartementNotFoundException;
	
	public Departement findDepartementByName(String nom);
	
	public Departement updateDepartement(Long id, Departement departement);

	public List<Departement> fetchDepartement();
	
	public List<Departement> fetchDepartementByName(String nom);

	public List<Departement> saveDepartementListe(List<Departement> departement);

	public String deleteDepartementById(Long id);


}
