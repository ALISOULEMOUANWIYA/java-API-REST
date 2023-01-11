package com.exemple.springbootmongodb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exemple.springbootmongodb.collection.Person;

public interface PersonRepository extends JpaRepository<Person, Integer>{
	List<Person> findByFirstName(String name);

	List<Person> findByAge(Integer age);
	
	void deleteById(Integer id);
}
