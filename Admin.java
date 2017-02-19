import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Admin extends User 
	implements AdminBehaviors, Serializable {
	
	/**
	 * Created: February 11, 2017
	 * By: David Klein
	 * Admin class inherits from the User super class and calls the superclass constructor when initializing
	 * 		It contains one extra variable, the menu variable, which has all the options of what the Admin class can do
	 * 		Admin implements the AdminBehaviors interface which contains the getMenu method and switchOperator method
	 * Admin returns nothing and only prints, writes to a file, and changes its own variables (which are later accessed in the main method for serialization)
	 */
	
	private static final long serialVersionUID = 1L;
	private String menu = "\nManage:\nEnter 1 to Create New Course\nEnter 2 to Delete A Course\n"
			+ "Enter 3 to Edit A Course\nEnter 4 to Display A Course\n"
			+ "Enter 5 to Register A Student\nEnter 6 to Exit\n\nReport:\nEnter 7 to View All Courses\nEnter 8 to View All Full Courses\n"
			+ "Enter 9 to Write All Full Courses To File\nEnter 10 to View Students By Course\n"
			+ "Enter 11 to View Courses by Student\nEnter 12 to Sort Courses By Registered Number\nEnter 6 to Exit: \n";
	
	public Admin(String one, String two, String three, String four) {
		super(one, two, three, four);
	}
	
	public String getMenu() {
		return menu;
	}
	
	//switchOperator takes in an integer choice from the user and initializes its own scanner object
	public void switchOperator(int choice) throws IOException, ClassNotFoundException {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		
		//Choice 1 one asks the user for information on a new course
		//		splits this information by commas
		//		and creates a new course with this information, 
		//			starting with zero current students and a String placeholder in the student string array
		//		and then calls a function to update the courselist with this new course
		if (choice == 1) {
			System.out.println("Separated by commas without spaces, please enter the Course Name, Course ID, "
					+ "Max Students,\n Course Instructor, Course Section Number, and Course Location: ");
			String[] info = input.next().split(",");
			String[] students = {"NULL"};
			Course newCourse = new Course(info[0], info[1], Integer.parseInt(info[2]), 0, students, info[3], Integer.parseInt(info[4]), info[5]);	
			updateData(newCourse);
		}
		
		//Choice 2 asks for the course name and section number for the course to delete
		//		iterates through the courselist until the name and section number match
		//		then removes this course from the courselist arraylist
		if (choice == 2) {
			System.out.println("Enter the course name you wish to delete: ");
			String name = input.nextLine();
			System.out.println("Enter the course section number you wish to delete: ");
			int section = input.nextInt();
			for (int i = 0; i < courseList.size(); ++i) {
				if (courseList.get(i).getCourseName().equals(name) && courseList.get(i).getSection() == section) {
					courseList.remove(i);
				}
			}
		}
		
		//Choice 3 asks for the course name and section number for the course to edit
		//		asks the user for the course info for the newly edited course
		//		then creates this new course
		//Iterates through the courselist arraylist until the matching course is found, 
		//		then removes this course from the courselist arraylist
		//		and then calls a function to update the courselist with this new course
		if (choice == 3) {
			System.out.println("Enter the course name you wish to edit: ");
			String name = input.nextLine();
			System.out.println("Enter the course section: ");
			int section = input.nextInt();
			
			System.out.println("Separated by commas without spaces, please enter the new Course Name, Course ID, "
					+ "Max Students,\n Course Instructor, Course Section Number, and Course Location: ");
			String[] info = input.next().split(",");
			String[] students = {"NULL"};
			Course newCourse = new Course(info[0], info[1], Integer.parseInt(info[2]), 0, students, info[3], Integer.parseInt(info[4]), info[5]);	
			
			for (int i = 0; i < courseList.size(); ++i) {
				if (courseList.get(i).getCourseName().equals(name) && courseList.get(i).getSection() == section) {
					newCourse = new Course(info[0], info[1], Integer.parseInt(info[2]), 0, courseList.get(i).getStudents(), info[3], Integer.parseInt(info[4]), info[5]);	
					courseList.remove(i);
				}
			}
			updateData(newCourse);
		}
		
		//Choice 4 asks for the course name and section number for the course to delete
		//		iterates through the courselist until the name and section number match
		//		then prints out this course
		if (choice == 4) {
			System.out.println("Enter the course name you wish to see: ");
			String name = input.nextLine();
			System.out.println("Enter the course section: ");
			int section = input.nextInt();
			for (int i = 0; i < courseList.size(); ++i) {
				if (courseList.get(i).getCourseName().equals(name) && courseList.get(i).getSection() == section) {
					System.out.println(courseList.get(i).toString());
				}
			}
		}
		
		//Choice 5 asks the user for student information to be make into a new student
		//		splits this information into a string array
		//		and uses the elements to create a new student account object
		//		and adds this student to the studentlist arraylist
		if (choice == 5) {
			System.out.println("To register a new student please separate by commas without spaces and enter\n"
					+ "Username, Password, First Name, and Last Name");
			String[] info = input.next().split(",");
			Student newStudent = new Student(info[0], info[1], info[2], info[3]);
			studentList.add(newStudent);
		}
		
		//Choice 7 calls the viewCourse method inherited from the User superclass
		if (choice == 7) {
			viewCourses();
		}
		
		//Choice 8 calls the viewFullCourses method inherited from the User superclass
		if (choice == 8) {
			viewVariableCourses(courseList);
		}
		
		//Choice 9 creates a new courselist arraylist from the instance variable
		//		and iterates through the arraylist and removes courses that have
		//			maximum students equalling current students
		//		then writes this arraylist to a Csv file by calling the writeToFile method
		//			inherited from the User superclass
		if (choice == 9) {
			ArrayList<Course> courseData = courseList;
			for (int i = 0; i < courseList.size(); ++i) {
				if (courseData.get(i).getMaxStudents() != courseData.get(i).getCurrentStudents()) {
					courseData.remove(i);
				}
			}
			writeToFile(courseData);
		}
		
		//Choice 10 asks for the course name and section number for the course to delete
		//		iterates through the courselist until the name and section number match	
		//		then prints out the string array of students from this course
		if (choice == 10) {
			System.out.println("Enter the course name you wish to see: ");
			String name = input.nextLine();
			System.out.println("Enter the course section: ");
			int section = input.nextInt();
			for (int i = 0; i < courseList.size(); ++i) {
				if (courseList.get(i).getCourseName().equals(name) && courseList.get(i).getSection() == section) {
					System.out.println(courseList.get(i).studentsToString());
				}
			}
		}
		
		//Choice 11 asks for the first and last name of the student
		//		iterates through the courselist to obtain the student string array from the course
		//		iterates through the student string array and checks to see if the name matches the user input name
		//		and prints the course out if there is a match
		if (choice == 11) {
			System.out.println("Enter the student name (first and last, no space) you wish to see: ");
			String name = input.next();
			for (int i = 0; i < courseList.size(); ++i) {
				String[] students = courseList.get(i).getStudents();
				for (int j = 0; j < students.length; ++j) {
					if (students[j].equals(name)) {
						System.out.println(courseList.get(i).toString());
					}
				}
			}
		}
		
		//Choice 12 calls the sortByNumberRegistered method inherited from the User superclass
		if (choice == 12) {
			sortByNumberRegistered();
		}
	}
	//viewFullCourses prints a header with the title of each section for the courses
	//		then checks to see if the maximum number of students is equal to the current number of students
	//		then if so, prints it out
	protected void viewVariableCourses(ArrayList<Course> courseData) throws ClassNotFoundException {
		System.out.println("Course_Name,Course_Id,Maximum_Students,Current_Students,List_Of_Names,Course_Instructor,Course_Section_Number,Course_Location");
		for (int i = 0; i < courseList.size(); ++i) {
			if (courseData.get(i).getMaxStudents() == courseData.get(i).getCurrentStudents()) {
				System.out.println(courseData.get(i));
			}
		}
		System.out.println("\n");
	}
	
}
