package com.exemple.springbootmongodb.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.exemple.springbootmongodb.collection.Person;

public interface PersonService {

	Person save(Person person);

	List<Person> getPersonStartWith();

	List<Person> findByName(String name);

	void deletePerson(Integer id);

	List<Person> findByAge(Integer age);

	Person findById(Person person, Integer id);

	Page<Person> search(String name, String age, String city, Pageable pageable);

}
