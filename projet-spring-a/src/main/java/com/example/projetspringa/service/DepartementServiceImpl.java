package com.example.projetspringa.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projetspringa.entity.Departement;
import com.example.projetspringa.error.DepartementNotFoundException;
import com.example.projetspringa.repository.DepartementRepository;


@Service
public class DepartementServiceImpl implements DepartementService{
	
	@Autowired
	private DepartementRepository departementsRpository;

	@Override
	public Departement saveDepartement(Departement departement) {
		return departementsRpository.save(departement);
	}

	@Override
	public List<Departement> fetchDepartement() {
		return departementsRpository.findAll();
	}
	
	@Override
	public Departement fetchDepartementById(Long id) throws DepartementNotFoundException {
		Optional<Departement> depart = 
				departementsRpository.findById(id);
		
		if (!depart.isPresent()) {
			throw new DepartementNotFoundException("Departement Not Available");
		}
		
		return depart.get();
	}

	@Override
	public String deleteDepartementById(Long id) {
		 departementsRpository.deleteById(id);
		 return "Department "+id+" is successfully deleted";
	}

	@Override
	public List<Departement> fetchDepartementByName(String nom) {
		return departementsRpository.findByDepartementName(nom);
	}

	@Override
	public List<Departement> saveDepartementListe(List<Departement> departement) {
		return departementsRpository.saveAll(departement);
	}

	@Override
	public Departement updateDepartement(Long id, Departement departement) {
		Departement dptDB = departementsRpository.findById(id).get();
		
		if (Objects.nonNull(departement.getDepartementName()) && 
				!"".equalsIgnoreCase(departement.getDepartementName())) {
			dptDB.setDepartementName(departement.getDepartementName());
		} 
		
		if (Objects.nonNull(departement.getDepartementCode()) && 
				!"".equalsIgnoreCase(departement.getDepartementCode())) {
			dptDB.setDepartementCode(departement.getDepartementCode());
		} 
		
		if (Objects.nonNull(departement.getDepartementAddress()) && 
				!"".equalsIgnoreCase(departement.getDepartementAddress())) {
			dptDB.setDepartementAddress(departement.getDepartementAddress());
		}
		return departementsRpository.save(dptDB);
		
		//return departementsRpository.findById(id).get();
	}

	@Override
	public Departement findDepartementByName(String nom) {
		return departementsRpository.findByDepartementNameIgnoreCase(nom);
	}
	
}
