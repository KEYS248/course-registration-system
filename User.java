import java.io.File;
import java.util.Collections;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;

public class User implements java.io.Serializable {
	/**
	 * Created: February 11, 2017
	 * By: David Klein
	 * User class is created by taking in 4 strings to specify username, password, firstName, and lastName
	 * 		It also contains two arraylists that are defined in the main method of the Program class
	 * There are getters for all variables
	 * 		and setters for the two arraylists
	 * Then there is one method that takes in an integer choice from the Program class's main method
	 * 		and this determines the action this user class will take
	 */
	private static final long serialVersionUID = 1L;
	protected String username;
	protected String password;
	protected String firstName;
	protected String lastName;
	protected ArrayList<Course> courseList;
	protected ArrayList<Student> studentList;
	
	public User(String username, String password, String firstName, String lastName) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setCourseList(ArrayList<Course> courseList) {
		this.courseList = courseList;
	}
	
	public void setStudentList(ArrayList<Student> studentList) {
		this.studentList = studentList;
	}
	
	public ArrayList<Course> getCourseList() {
		return courseList;
	}
	
	public ArrayList<Student> getStudentList() {
		return studentList;
	}
	
	/*
	 * importData sets an arraylist for courseData, where it scans for file named MyUniversityCourses.csv
	 * 		skips the top header and reads each line, separating each section by commas
	 * 		and creating a course object for each line and adding it to the courseData arraylist
	 * 		then returns this arraylist
	 */
	protected ArrayList<Course> importData() {
		ArrayList<Course> courseData = new ArrayList<Course>();
		try{
			File universityData = new File("MyUniversityCourses.csv");
			Scanner intake = new Scanner(universityData);
				while (intake.hasNextLine()) {
					String line = intake.nextLine();
					if (!line.contains("Course_Name,Course_Id,Maximum_Students,Current_Students,List_Of_Names,Course_Instructor,Course_Section_Number,Course_Location")){
						String[] splitLine = line.split(",");
						String courseName = splitLine[0];
						String courseID = splitLine[1];
						int maxStudents = Integer.parseInt(splitLine[2]);
						int currentStudents = Integer.parseInt(splitLine[3]);
						String[] students = splitLine[4].split("-");
						String instructor = splitLine[5];
						int section = Integer.parseInt(splitLine[6]);
						String location = splitLine[7];
						Course tempCourse = new Course(courseName, courseID, maxStudents, currentStudents, students, instructor, section, location);
						courseData.add(tempCourse);
					}
		}
		intake.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return courseData;
	}
	
	//viewCourses prints a header with the title of each section for the courses
	//		then prints each course
	protected void viewCourses() throws ClassNotFoundException {
		System.out.println("Course_Name,Course_Id,Maximum_Students,Current_Students,List_Of_Names,Course_Instructor,Course_Section_Number,Course_Location");
		for (int i = 0; i < courseList.size(); ++i) {
			System.out.println(courseList.get(i));
		}
		System.out.println("\n");
	}
	
	//viewFullCourses prints a header with the title of each section for the courses
	//		then checks to see if the maximum number of students is equal to the current number of students
	//		then if so, prints it out
	protected void viewVariableCourses() throws ClassNotFoundException {
	}
	
	//updateData takes in a course object, and if the object exists within the courseList ArrayList
	//		replaces this the course in the arraylist with the new course object from the parameter
	//if there doesnt exist a matching course then it adds the course object to the arraylist
	protected void updateData(Course course) throws IOException, ClassNotFoundException {
		boolean addDecision = true;
		for (int i = 0; i < courseList.size(); ++i) {
			if (courseList.get(i).getCourseName().equals(course.getCourseName()) && courseList.get(i).getSection() == course.getSection()) {
				courseList.set(i, course);
				addDecision = false;
			}
		}
		if (addDecision) {
			courseList.add(course);
		}
	}
	
	//studentsInCourse takes a course object and gets the students (which exists as an array) from this course
	//		then it iterates through the string array and prints each element out
	protected void studentsInCourse(Course course) {
		String[] students = course.getStudents();
		for (int i = 0; i < students.length; ++i) {
			System.out.println(students[i]);
		}
	}
	
	//studentCourses takes a student string, iterates through the courselist arraylist
	//		to get the student string array from each course
	//		iterates through that student string array and 
	//		then checks to see if each element in the student string array is equal to the student string from the parameters
	//		and if equal then prints out the course name
	protected void studentCourses(String student) {
		for (int i = 0; i < courseList.size(); ++i) {
			String[] students = courseList.get(i).getStudents();
			for (int j = 0; j < students.length; ++j) {
				if (students[i] == student) {
					System.out.println(courseList.get(i).getCourseName());
				}
			}
		}
	}
	
	//sortByNumberRegistered uses collections interface to sort through the courselist
	// 		and order the courses by number of students registered, starting with the most at the top
	protected ArrayList<Course> sortByNumberRegistered() throws ClassNotFoundException {
		Collections.sort(courseList);
		return courseList;
	}
	
	//writeToFile takes in an arraylist of courses then iterates through these courses
	//		adding each course to a string
	//		then writes this string into a new file called FullCourses.csv
	protected void writeToFile(ArrayList<Course> courseData) throws IOException {
		String output = "";
		for (int i = 0; i < courseData.size(); ++i) {
			Course temp = courseData.get(i);
			output += temp.toString() + "\n";
		}
		File file = new File("FullCourses.csv");
		FileWriter writer = new FileWriter(file);
		writer.write(output);
		writer.close();
	}
	
}
