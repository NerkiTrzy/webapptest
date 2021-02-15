package com.proquest.interview.phonebook;

public class Person {
	private String name;
	private String phoneNumber;
	private String address;

	public Person(String name, String phoneNumber, String address) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	@Override
	public String toString() {
		return "Full name: " + this.name + " Phone number: " + this.phoneNumber + " Address: " + this.address;
	}
}
