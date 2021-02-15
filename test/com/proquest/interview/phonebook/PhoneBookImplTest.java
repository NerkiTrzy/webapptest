package com.proquest.interview.phonebook;

import com.proquest.interview.util.DatabaseUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class PhoneBookImplTest {

	private final PhoneBookImpl phoneBook = new PhoneBookImpl();

	@Before
	public void init() {
		DatabaseUtil.initDB();
	}

	@After
	public void cleanUp() {
		//  Clear test table
		try {
			Connection c =  DatabaseUtil.getConnection();
			c.createStatement().execute("DROP TABLE PHONEBOOK");
			c.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void shouldAddFindPerson() {
		String firstTestName = "testFirstName";
		String lastTestName = " testLastName";
		Person testPerson = new Person(firstTestName + " " + lastTestName, "testNumber", "testAddress");
		this.phoneBook.addPerson(testPerson);

		Person resultPerson = this.phoneBook.findPerson(firstTestName, lastTestName);

		Assert.assertEquals(resultPerson.getName(), testPerson.getName());
		Assert.assertEquals(resultPerson.getPhoneNumber(), testPerson.getPhoneNumber());
		Assert.assertEquals(resultPerson.getAddress(), testPerson.getAddress());
	}
}
