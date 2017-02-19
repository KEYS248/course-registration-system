import java.io.Serializable;

public class Course implements Comparable<Course>, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String courseName;
	private String courseID;
	private int maxStudents;
	private int currentStudents;
	private String[] students;
	private String instructor;
	private int section;
	private String location;
	
	public Course(String one, String two, int three, int four, String[] five, String six, int seven, String eight) {
		courseName = one;
		courseID = two;
		maxStudents = three;
		currentStudents = four;
		students = five;
		instructor = six;
		section = seven;
		location = eight;
	}
	
	public String getCourseName() {
		return courseName;
	}
	
	public String getCourseID() {
		return courseID;
	}
	
	public int getMaxStudents() {
		return maxStudents;
	}
	public int getCurrentStudents() {
		return currentStudents;
	}
	
	public String[] getStudents() {
		return students;
	}
	
	public String getInstructor() {
		return instructor;
	}
	
	public int getSection() {
		return section;
	}
	
	public String getLocation() {
		return location;
	}
	
	public String toString() {
		String output = courseName + "," + courseID + "," + Integer.toString(maxStudents) + "," + Integer.toString(currentStudents) + "," + this.studentsToString() + "," + instructor + "," + Integer.toString(section) + "," + location;
		return output;
	}
	
	public int compareTo(Course course) {
		if (this.getCurrentStudents() < course.getCurrentStudents()) {
			return 1;
		}
		else if (this.getCurrentStudents() > course.getCurrentStudents()) {
			return -1;
		}
		else {
			return 0;
		}
	}
	
	public String studentsToString() {
		String studentString = "";
		for (int i = 0; i < this.getStudents().length; ++i) {
			studentString += students[i];
		}
		return studentString;
	}
	
}
