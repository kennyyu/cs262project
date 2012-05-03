package edu.harvard.cs262.grading.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.harvard.cs262.grading.server.services.AssignmentImpl;
import edu.harvard.cs262.grading.server.services.Grade;
import edu.harvard.cs262.grading.server.services.GradeImpl;
import edu.harvard.cs262.grading.server.services.GradeStorageService;
import edu.harvard.cs262.grading.server.services.MongoGradeStorageService;
import edu.harvard.cs262.grading.server.services.ScoreImpl;
import edu.harvard.cs262.grading.server.services.Student;
import edu.harvard.cs262.grading.server.services.StudentImpl;
import edu.harvard.cs262.grading.server.services.Submission;
import edu.harvard.cs262.grading.server.services.SubmissionImpl;

public class TestGradeStorageService {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testAssignment() throws Exception {
		GradeStorageService service = new MongoGradeStorageService();
		service.init();

		byte[] content = new byte[42];

		Submission sub1 = new SubmissionImpl(new StudentImpl(0),
				new AssignmentImpl(), content);

		// Oh no, they submitted the same assignment! Plagiarism!
		Submission sub2 = new SubmissionImpl(new StudentImpl(1),
				new AssignmentImpl(), content);

		Student grader1 = new StudentImpl(2);
		Student grader2 = new StudentImpl(3);
		Student grader3 = new StudentImpl(4);

		Grade grade1 = new GradeImpl(new ScoreImpl(99, 100), grader1, "Nice!");
		Grade grade2 = new GradeImpl(new ScoreImpl(50, 100), grader2,
				"This sucks!");
		Grade grade3 = new GradeImpl(new ScoreImpl(80, 100), grader3, "Meh.");

		service.submitGrade(sub1, grade1);
		service.submitGrade(sub1, grade2);
		service.submitGrade(sub2, grade3);

		List<Grade> grades1 = service.getGrade(sub1);
		List<Grade> grades2 = service.getGrade(sub2);

		assertTrue(grades1.size() == 2);
		assertTrue(grades1.contains(grade1));
		assertTrue(grades1.contains(grade2));

		assertTrue(grades2.size() == 1);
		assertTrue(grades2.contains(grade3));
		assertFalse(grades2.contains(grade1));
	}

	@After
	public void tearDown() throws Exception {
	}

}
