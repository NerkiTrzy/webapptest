package com.proquest.interview.phonebook;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.proquest.interview.util.DatabaseUtil;

public class PhoneBookImpl implements PhoneBook {
	private List<Person> people;

	public PhoneBookImpl() {
		this.people = new ArrayList<>();
	}

	@Override
	public void addPerson(Person newPerson) {
		try {
			Connection connection = DatabaseUtil.getConnection();
			String query = " INSERT INTO PHONEBOOK (NAME, PHONENUMBER, ADDRESS)"
					+ " VALUES (?, ?, ?);";

			PreparedStatement preparedStmt = connection.prepareStatement(query);
			preparedStmt.setString(1, newPerson.getName());
			preparedStmt.setString(2, newPerson.getPhoneNumber());
			preparedStmt.setString(3, newPerson.getAddress());

			preparedStmt.execute();

			connection.close();

		} catch (SQLException | ClassNotFoundException throwables) {
			throwables.printStackTrace();
		}
	}

	@Override
	public List<Person> findAllPeople() {
		List<Person> people = new ArrayList<>();
		try {
			Connection connection = DatabaseUtil.getConnection();
			String query = " SELECT name, phonenumber, address FROM PHONEBOOK;";

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				people.add(new Person(resultSet.getString("name"),
						resultSet.getString("phonenumber"),
						resultSet.getString("address")));
			}
			connection.close();

		} catch (SQLException | ClassNotFoundException throwables) {
			throwables.printStackTrace();
		}

		return people;
	}

	@Override
	public Person findPerson(String firstName, String lastName) {
		String fullName = firstName + " " + lastName;
		Person resultPerson = null;

		try {
			Connection connection = DatabaseUtil.getConnection();
			String query = " SELECT name, phonenumber, address FROM PHONEBOOK WHERE name = ?;";

			PreparedStatement preparedStmt = connection.prepareStatement(query);
			preparedStmt.setString(1, fullName);

			ResultSet resultSet = preparedStmt.executeQuery();
			resultSet.next();
			resultPerson = new Person(resultSet.getString("name"),
					resultSet.getString("phonenumber"),
					resultSet.getString("address"));

			connection.close();

		} catch (SQLException | ClassNotFoundException throwables) {
			throwables.printStackTrace();
		}

		return resultPerson;
	}

	public List<Person> getPeople() {
		return people;
	}

	public static void main(String[] args) {
		DatabaseUtil.initDB();  //You should not remove this line, it creates the in-memory database

		PhoneBookImpl phoneBook = new PhoneBookImpl();


		/* TODO: create person objects and put them in the PhoneBook and database
		 * John Smith, (248) 123-4567, 1234 Sand Hill Dr, Royal Oak, MI
		 * Cynthia Smith, (824) 128-8758, 875 Main St, Ann Arbor, MI
		 */
		Person john = new Person("John Smith", "(248) 123-4567", "1234 Sand Hill Dr, Royal Oak, MI");
		Person cynthia = new Person("Cynthia Smith", "(824) 128-8758", "875 Main St, Ann Arbor, MI");

		phoneBook.addPerson(john);
		phoneBook.addPerson(cynthia);

		// TODO: print the phone book out to System.out
		for (Person person: phoneBook.findAllPeople()) {
			System.out.println(person.toString());
		}

		// TODO: find Cynthia Smith and print out just her entry
		System.out.println("Find Cynthia Smith");
		System.out.println(phoneBook.findPerson("Cynthia", "Smith"));

		// TODO: insert the new person objects into the database
		Person przemyslaw = new Person("Przemyslaw Roguski", "7441427017", "Crawley 44 Oak Way RH10 8HU");
		phoneBook.addPerson(przemyslaw);
		System.out.println("Find Przemyslaw Roguski");
		System.out.println(phoneBook.findPerson("Przemyslaw", "Roguski"));

		//  Clear test table
		try {
			Connection c =  DatabaseUtil.getConnection();
			c.createStatement().execute("DROP TABLE PHONEBOOK");
			c.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
