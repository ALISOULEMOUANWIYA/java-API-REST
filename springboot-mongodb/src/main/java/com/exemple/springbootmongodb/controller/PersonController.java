package com.exemple.springbootmongodb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;

import com.exemple.springbootmongodb.collection.Person;
import com.exemple.springbootmongodb.service.PersonService;

import javax.validation.Valid;


@RestController
@RequestMapping("/person")
public class PersonController {

	@Autowired
	private PersonService personService;
	
	@PostMapping("/persons")
	public Person save(@Valid @RequestBody Person person) {
		return personService.save(person);
	}
	
	
	  @GetMapping("/persons")
	  public List<Person> getPersonStartWith() {
	    return personService.getPersonStartWith();
	  }

	  @GetMapping("/personsone")
	  public List<Person> findByName(@RequestParam("name") String name) {
	    return personService.findByName(name);
	  }
	  
	  @GetMapping("/personsage")
	  public List<Person> findByAge(@RequestParam("age") Integer age) {
		 return personService.findByAge(age);
	  }
	  
	  @DeleteMapping("/deleteUser/{id}")
	  public void deletePerson(@PathVariable Integer id) {
	     personService.deletePerson(id);
	  }
	  
	  @GetMapping("/search")
	  public Page<Person> searchPerson(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String age,
			@RequestParam(required = false) String city,
			@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "5") Integer size
	   ){
		  Pageable pageable = PageRequest.of(page, size);
		  return personService.search(name, age, city, pageable);
	   }

	  /*@PutMapping("/persons/{id}")
	  public Person replacePerson(@RequestBody Person newPersons, @PathVariable Integer id) {
	    
	    return personService.findById(newPersons, id);
	      
	  }*/
}
