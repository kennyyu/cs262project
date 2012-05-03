package edu.harvard.cs262.grading.test;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import edu.harvard.cs262.grading.server.services.Student;
import edu.harvard.cs262.grading.server.services.StudentService;
import edu.harvard.cs262.grading.server.services.StudentServiceServer;

public class TestStudentService {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testStudent() throws Exception {
		StudentService service = new StudentServiceServer();
		service.init();

		service.addNewStudent(0, "student@college.edu", "Joe", "Student");
		service.addNewStudent(1, "bovik@cs.cmu.edu", "Harry", "Bovik");

		Student joe = service.getStudent(0);
		Student bovik = service.getStudent(1);

		assertTrue(joe.email().equals("student@college.edu"));
		assertTrue(joe.firstName().equals("Joe"));
		assertTrue(bovik.lastName().equals("Bovik"));

		Set<Student> students = service.getStudents();
		assertTrue(students.size() == 2);
		assertTrue(students.contains(joe));
		assertTrue(students.contains(bovik));
	}

}
