import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

public class Student extends User
	implements StudentBehaviors, Serializable {
	
	/**
	 * Created: February 11, 2017
	 * By: David Klein
	 * Student class inherits from the User super class and calls the superclass constructor when initializing
	 * 		It contains one extra variable, the menu variable, which has all the options of what the Student class can do
	 * 		Student implements the AdminBehaviors interface which contains the getMenu method and switchOperator method
	 * Student returns nothing and only prints and changes its own variables (which are later accessed in the main method for serialization)
	 */
	
	private static final long serialVersionUID = 1L;
	private String menu = "\nManage:\nEnter 1 to View All Courses\nEnter 2 to View All Open Courses\n"
			+ "Enter 3 to Register For Course\nEnter 4 to Withdraw From Course\n"
			+ "Enter 5 to View Current Registered Courses\nEnter 6 to Exit: \n";
	
	public Student(String one, String two, String three, String four) {
		super(one, two, three, four);
	}
	
	public String getMenu() {
		return menu;
	}
	
	//switchOperator takes in an integer choice from the user and initializes its own scanner object
	public void switchOperator(int choice) throws IOException, ClassNotFoundException {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		
		//Choice 1 calls the viewCourse method inherited from the User superclass
		if (choice == 1) {
			viewCourses();
		}
		
		//Choice 2 calls the viewOpenCourse method inherited from the User superclass
		if (choice == 2) {
			viewVariableCourses();
		}
		
		//Choice 2 asks for the course name and section number for the course to register for
		//		iterates through the courselist until the name and section number match
		//		if the course has the placeholder string NULL in the students string array
		//			then a new string array will be created with only the new student in it
		//			the course will be recreated with this information and will call the updateData method inherited from the User superclass
		//		if the course does not have the placeholder string NULL in the students string array
		//			then a new string array will be created that is one element larger
		//			each element in the old array will be iterated into the new element
		//			and the new student will be added to the last element
		//		then a new course will be created with this new string array
		//			and updateData method will be called with the newly created course
		if (choice == 3) {
			System.out.println("Enter the course name you wish to register for: ");
			String name = input.nextLine();
			System.out.println("Enter the course section: ");
			int section = input.nextInt();
			for (int i = 0; i < courseList.size(); ++i) {
				if (courseList.get(i).getCourseName().equals(name) && courseList.get(i).getSection() == section) {
					if (courseList.get(i).getStudents()[0].equals("NULL")) {
						String student = this.firstName + this.lastName;
						String[] studentsNew = {student};
						Course course = new Course(courseList.get(i).getCourseName(), courseList.get(i).getCourseID(), courseList.get(i).getMaxStudents(), courseList.get(i).getCurrentStudents()+1, studentsNew, courseList.get(i).getInstructor(), courseList.get(i).getSection(), courseList.get(i).getLocation());
						updateData(course);
					}
					else {
						String[] students = courseList.get(i).getStudents();
						String[] studentsNew = new String[students.length + 1];
						for (int j = 0; j < studentsNew.length; ++j) {
							if (i == studentsNew.length - 1) {
								studentsNew[i] = this.firstName + this.lastName; 
							}
							else {
								studentsNew[i] = students[i];
							}
						}
						Course course = new Course(courseList.get(i).getCourseName(), courseList.get(i).getCourseID(), courseList.get(i).getMaxStudents(), courseList.get(i).getCurrentStudents()+1, studentsNew, courseList.get(i).getInstructor(), courseList.get(i).getSection(), courseList.get(i).getLocation());
						updateData(course);
					}
				}
			}
		}
		
		//Choice 2 asks for the course name and section number for the course to withdraw from
		//		iterates through the courselist until the name and section number match
		//		if the course has student string longer than 1 element
		//			then a new string array will be created with all elements except that one
		//			the course will be recreated with this information and will call the updateData method inherited from the User superclass
		//		if the course has student string with only 1 element
		//			then a new string array will be created with only the placeholder string NULL
		//			then a new course will be created with this new string array
		//			the course will be recreated with this information and will call the updateData method inherited from the User superclass
		if (choice == 4) {
			System.out.println("Enter the course name you wish to withdraw from: ");
			String name = input.nextLine();
			System.out.println("Enter the course section: ");
			int section = input.nextInt();
			for (int i = 0; i < courseList.size(); ++i) {
				if (courseList.get(i).getCourseName().equals(name) && courseList.get(i).getSection() == section) {
					String[] students = courseList.get(i).getStudents();
					if (students.length > 1) {
						String[] studentsNew = new String[students.length - 1];
						String student = this.firstName + this.lastName;
						for (int j = 0; j < studentsNew.length; ++j) {
							if (students[i] == student) {
								continue;
							}
							studentsNew[i] = students[i];
							
						}
						Course course = new Course(courseList.get(i).getCourseName(), courseList.get(i).getCourseID(), courseList.get(i).getMaxStudents(), courseList.get(i).getCurrentStudents()-1, studentsNew, courseList.get(i).getInstructor(), courseList.get(i).getSection(), courseList.get(i).getLocation());
						updateData(course);
					}
					else {
						String[] studentsNew = {"NULL"};
						Course course = new Course(courseList.get(i).getCourseName(), courseList.get(i).getCourseID(), courseList.get(i).getMaxStudents(), courseList.get(i).getCurrentStudents()-1, studentsNew, courseList.get(i).getInstructor(), courseList.get(i).getSection(), courseList.get(i).getLocation());
						updateData(course);
					}
				}
			}
		}
		
		//Choice 5 iterates through the courselist arraylist
		//		creates a string array with the students string array from the course
		//		and iterates through the array to check if this student's first and last name match that in the string array
		//		and if so then prints it out
		if (choice == 5) {
			String name = this.firstName + this.lastName;
			for (int i = 0; i < courseList.size(); ++i) {
				String[] students = courseList.get(i).getStudents();
				for (int j = 0; j < students.length; ++j) {
					if (students[j].equals(name)) {
						System.out.println(courseList.get(i).toString());
					}
				}
			}
		}
	}
	
	//viewOpenCourses prints the header with the title of each section for the courses
	//		then checks to see if the maximum number of students is equal to the current number of students
	//		if not, then prints it out
	protected void viewVariableCourses() throws ClassNotFoundException {
		System.out.println("Course_Name,Course_Id,Maximum_Students,Current_Students,List_Of_Names,Course_Instructor,Course_Section_Number,Course_Location");
		for (int i = 0; i < courseList.size(); ++i) {
			if (courseList.get(i).getMaxStudents() != courseList.get(i).getCurrentStudents()) {
				System.out.println(courseList.get(i));
			}
		}
		System.out.println("\n");
	}

}
