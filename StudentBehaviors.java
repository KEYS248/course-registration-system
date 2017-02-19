import java.io.IOException;

	/*
	 * Created: February 11, 2017
	 * By: David Klein
	 * AdminBehaviors is an interface for the Student class
	 * 		It defines the two methods present in the Student class
	 */

public interface StudentBehaviors {

	//getMenu will return a String of all the options an Admin class can do that a user can decide upon
	String getMenu();
	
	//switchOperator will take in a decision from the user in the form of an integer from the main method
	//		and execute actions based on this decision
	void switchOperator(int choice) throws IOException, ClassNotFoundException;
	
}
