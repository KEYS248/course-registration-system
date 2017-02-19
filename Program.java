import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.Serializable;

	/*
	 * Created: February 11, 2017
	 * By: David Klein
	 * Function: Runs the main method function for a Course Registration System
	 * 				includes one static method used for handling numeric user input
	 * 		It reads in a CSV file named "MyUniversityCourses.csv" or a Ser file names "Courses.ser" with course information
	 * 			and reads in a Ser file names "Students.ser" if it exists
	 * 			and saves both into separate Ser files at the end of the program
	 * 		Allows one Administration login with username: Admin, password: Admin001
	 * 				Administration can then create Student accounts for student login
	 * 		These accounts allow for a number of different functions for registering and withdrawing students to courses
	 * 				and creating, deleting, editing, sorting, and printing courses
	 */

public class Program implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		//Course List and Student List created to be used throughout program 
		//	if student list Ser file exists then the program will deserialize from Students.ser
		//	otherwise it will read from a csv file
		Admin admin = new Admin("Admin", "Admin001", "Ad", "Min");
		ArrayList<Course> courseList = new ArrayList<Course>();
		ArrayList<Student> studentList = new ArrayList<Student>();
		File courseFile = new File("Courses.ser");
		File studentFile = new File("Students.ser");
		if(courseFile.exists() && studentFile.exists()) {
			try{
				FileInputStream fis = new FileInputStream("Students.ser");
				ObjectInputStream ois = new ObjectInputStream(fis);
				studentList = (ArrayList<Student>) ois.readObject();
				ois.close();
				fis.close();
		    } catch(IOException ioe) {
		    	ioe.printStackTrace();
		    } catch (ClassNotFoundException cnfe) {
		 		studentList = new ArrayList<Student>();
		 		cnfe.printStackTrace();
		 	}
			try{
				FileInputStream fis = new FileInputStream("Courses.ser");
				ObjectInputStream ois = new ObjectInputStream(fis);
				courseList = (ArrayList<Course>) ois.readObject();
				ois.close();
				fis.close();
		    } catch(IOException ioe) {
		    	ioe.printStackTrace();
		    } catch (ClassNotFoundException cnfe) {
		 		cnfe.printStackTrace();
		    }
		}
		else {
			courseList = admin.importData();
			studentList = new ArrayList<Student>();
		}
		
		int choice = 0;
		Scanner in = new Scanner(System.in);
		int[] startLimits = {1, 2};
		
		//User determines if they want to log in as student or admin account
		//		then asks for user for their username and password
		String input = "Enter '1' for Student login and '2' for Admin login: ";
		int login = inputValidator(input, in, startLimits);
		System.out.println("Please enter your username: ");
		String username = in.next();
		System.out.println("Please enter your password: ");
		String password = in.next();
		
		/*
		 * If student login, will iterate through student list for matching student account
		 * 		will set the student's studentlist instance variable equal to the main's studentlist variable
		 * 		and the same with the courselist
		 * User will choose a choice from a printed menu retrieved from within the student class
		 * 		choosing 6 will end the menu loop
		 * Finally the main's studentlist and courselist variables will be made equal to the student's
		 * 		instance variables of the same name
		 */
		if (login == 1 && studentList.size() > 0) {
			for (int i = 0; i < studentList.size(); ++i) {
				if (studentList.get(i).getUsername().equals(username) && studentList.get(i).getPassword().equals(password)) {
					Student student = studentList.get(i);
					student.setCourseList(courseList);
					student.setStudentList(studentList);
					int[] choiceLimits= {1, 6};
					choice = inputValidator(student.getMenu(), in, choiceLimits);
					student.switchOperator(choice);
					while (choice != 6) {
						choice = inputValidator(student.getMenu(), in, choiceLimits);
						student.switchOperator(choice);
					}
					courseList = student.getCourseList();
					studentList = student.getStudentList();
				}
			}
		}
		
		// If student login, this if statement will follow the same procedures as the one above
		//		but will not iterate through a list to find an admin because there is only one
		if (login == 2) {
			if (admin.username.equals(username) && admin.password.equals(password)) {
				admin.setCourseList(courseList);
				admin.setStudentList(studentList);
				int[] choiceLimits= {1, 13};
				choice = inputValidator(admin.getMenu(), in, choiceLimits);
				admin.switchOperator(choice);
				while (choice != 6) {
					choice = inputValidator(admin.getMenu(), in, choiceLimits);
					admin.switchOperator(choice);
				}
				courseList = admin.getCourseList();
				studentList = admin.getStudentList();
			}
		}
		
		/*
		 * If the username or password are incorrect or that account does not exist this will print
		 * 		because the choice variable has not been changed within one of the two above if statements
		 * but if the username and password are correct and the account does exist then the program
		 * 		will serialize a Students.ser file for the studentslist
		 * 		and a Courses.ser file for the courselist
		 * 		and end the program
		 */
		if (choice == 0) {
			System.out.println("Sorry, incorrect username or password, please restart program");
		}
		else {
			try{
				FileOutputStream fos = new FileOutputStream("Students.ser");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(studentList);
				oos.close();
				fos.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			try{
				FileOutputStream fos = new FileOutputStream("Courses.ser");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(courseList);
				oos.close();
				fos.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		in.close();
	}
	
	//This method takes in a String to be printed to the user as instructions
	//	and takes in an int array of allowed inputs from the user that will allow a decision to be made
	//	and it takes in the scanner object so object doesn't have to be closed
	//If the user decision is not within the int array or is a string,
	//	the method will loop until the user chooses a correct response
	//Then method will return this integer decision
	public static int inputValidator(String output, Scanner in, int[] allowed) {
		int input = 0;
		boolean repeater = true;
		
		while (repeater) {
			System.out.println(output);
			if (in.hasNextInt()) {
				input = in.nextInt();
				if (input >= allowed[0] && input <= allowed[1]) {
					repeater = false;
				}
				else {
					System.out.println("Error, please enter a valid choice");
				}
			}
			else if (repeater) {
				System.out.println("Error, please enter a valid choice");
				in.next();
				continue;
			}
		}
		return input;
	}
}
