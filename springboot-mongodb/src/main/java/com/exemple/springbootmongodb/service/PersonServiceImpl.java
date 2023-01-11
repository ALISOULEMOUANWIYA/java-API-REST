package com.exemple.springbootmongodb.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.exemple.springbootmongodb.collection.Person;
import com.exemple.springbootmongodb.repository.PersonRepository;

@Service
public class PersonServiceImpl implements PersonService{
	
	@Autowired
	private PersonRepository personRepository;

	@Override
	public Person save(Person person) {
		return personRepository.save(person);
	}

	@Override
	public List<Person> getPersonStartWith() {
		return personRepository.findAll();
	}


	@Override
	public List<Person> findByName(String name) {
		return personRepository.findByFirstName(name);
	}

	@Override
	public void deletePerson(Integer id) {
		personRepository.deleteById(id); 
	}

	@Override
	public List<Person> findByAge(Integer age) {
		return personRepository.findByAge(age);
	}

	@Override
	public Person findById(Person person, Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Person> search(String name, String age, String city, Pageable pageable) {
	    
		
		return null;
	}
	


	/*@Override
	public Person findById(Person newPerson, Integer id) {
		return personRepository.findById(id)
			      .map(person -> {
			    	  person.setFirstName(newPerson.getFirstName());
			    	  person.setLastName(newPerson.getLastName());
			        return repository.save(person);
			      })
			      .orElseGet(() -> {
			    	  newPerson.setId(id);
			        return repository.save(newPerson);
			      });
	}*/
	
}
