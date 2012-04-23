package edu.harvard.cs262.grading;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.harvard.cs262.grading.Assignment;
import edu.harvard.cs262.grading.Grade;
import edu.harvard.cs262.grading.GradeCompilerService;
import edu.harvard.cs262.grading.Score;
import edu.harvard.cs262.grading.Student;
import edu.harvard.cs262.grading.Submission;


public class GradeCompilerServiceServer implements GradeCompilerService {

	private ConfigReader config;
	
	public GradeCompilerServiceServer() {
		config = new ConfigReaderImpl();
	}
	
	@Override
	public void init() throws Exception {
	}

	@Override
	public Grade storeGrade(Student grader, Submission submission, Score score)
			throws RemoteException {
		Grade grade = new GradeImpl(score, grader);
		List<String> registryNames = config.getRegistryLocations("GradeStorageService");
		for (int j = 0; j < registryNames.size(); j++) {
			try {
				Registry registry = LocateRegistry.getRegistry(registryNames.get(j));
				GradeStorageService storage = (GradeStorageService) registry.lookup("GradeStorageService");
				storage.submitGrade(submission, grade);
				return grade;
			} catch (RemoteException e) {
				if (j + 1 == registryNames.size())
					throw e;
			} catch (NotBoundException e) {
				if (j + 1 == registryNames.size()) {
					System.err.println("Looking up GradeStorageService failed");
					System.exit(-1);
				}
			}
		}
		return grade;
	}

	@Override
	public Set<Student> getGraders(Submission submission)
			throws RemoteException {
		// get the list of grades for this submission
		List<Grade> grades = new ArrayList<Grade>();
		List<String> gradeStorageServiceNames = config.getRegistryLocations("GradeStorageService");
		for (int j = 0; j < gradeStorageServiceNames.size(); j++) {
			try {
				Registry registry = LocateRegistry.getRegistry(gradeStorageServiceNames.get(j));
				GradeStorageService storage = (GradeStorageService) registry.lookup("GradeStorageService");
				grades = storage.getGrade(submission);
			} catch (RemoteException e) {
				if (j + 1 == gradeStorageServiceNames.size())
					throw e;
			} catch (NotBoundException e) {
				if (j + 1 == gradeStorageServiceNames.size()) {
					System.err.println("Looking up SubmissionStorageService failed");
					System.exit(-1);
				}
			}
		}
		
		// get the graders from the grades
		Set<Student> graders = new HashSet<Student>();
		for (Grade g : grades)
			graders.add(g.getGrader());
		return graders;
	}

	@Override
	public Map<Submission, List<Grade>> getCompiledGrades(Assignment assignment)
			throws RemoteException {
		// get the list of submissions for this assignment
		Set<Submission> submissions = new HashSet<Submission>();
		List<String> submissionStorageServiceNames = config.getRegistryLocations("SubmissionStorageService");
		for (int j = 0; j < submissionStorageServiceNames.size(); j++) {
			try {
				Registry registry = LocateRegistry.getRegistry(submissionStorageServiceNames.get(j));
				SubmissionStorageService storage = (SubmissionStorageService) registry.lookup("SubmissionStorageService");
				submissions = storage.getAllSubmissions(assignment);
			} catch (RemoteException e) {
				if (j + 1 == submissionStorageServiceNames.size())
					throw e;
			} catch (NotBoundException e) {
				if (j + 1 == submissionStorageServiceNames.size()) {
					System.err.println("Looking up SubmissionStorageService failed");
					System.exit(-1);
				}
			}
		}
		
		// for each submission, retrieve the list of grades for that submission
		Map<Submission, List<Grade>> grades = new HashMap<Submission, List<Grade>>();
		List<String> gradeStorageServiceNames = config.getRegistryLocations("GradeStorageService");
		for (int j = 0; j < gradeStorageServiceNames.size(); j++) {
			try {
				Registry registry = LocateRegistry.getRegistry(gradeStorageServiceNames.get(j));
				GradeStorageService storage = (GradeStorageService) registry.lookup("GradeStorageService");
				for (Submission s : submissions)
					grades.put(s, storage.getGrade(s));
				return grades;
			} catch (RemoteException e) {
				if (j + 1 == gradeStorageServiceNames.size())
					throw e;
			} catch (NotBoundException e) {
				if (j + 1 == gradeStorageServiceNames.size()) {
					System.err.println("Looking up GradeStorageService failed");
					System.exit(-1);
				}
			}
		}
		return grades;
	}
	
	public static void main(String[] args) {
		try {
			GradeCompilerServiceServer obj = new GradeCompilerServiceServer();
			obj.init();
			GradeCompilerService stub = (GradeCompilerService) UnicastRemoteObject.exportObject(obj, 0);
			
			// bind the remote object's stub in the registry
			Registry registry = LocateRegistry.getRegistry();
			
			// check for registry update command
			boolean forceUpdate = false;
			for(int i = 0, len = args.length; i < len; i++)
				if(args[i].equals("--update")) forceUpdate = true;

			if(forceUpdate) {
				registry.rebind("GradeCompilerService", stub);
			} else {
				registry.bind("GradeCompilerService", stub);
			}

			System.err.println("GradeCompilerService running");
			
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}

}
