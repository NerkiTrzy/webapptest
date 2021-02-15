package com.proquest.interview.phonebook;

import java.util.List;

public interface PhoneBook {
	Person findPerson(String firstName, String lastName);
	void addPerson(Person newPerson);
	List<Person> findAllPeople();
}
