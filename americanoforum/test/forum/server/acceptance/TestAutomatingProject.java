package Tests;



import java.util.Set;

import junit.framework.TestCase;

/**
 * The father class of each test class we will write, it contains
 * the main methods of the tested operations.
 */
public class TestAutomatingProject extends TestCase{
	//the bridge to the real implementation , or the proxy bridge that will
	//simulate the run in a case that a real bridge doesn't exist.
	private static ForumBridge _bridge;

	public TestAutomatingProject() {
		super();
	}

	/**
	 * The login method that each user : Dept. employee or "OSH" representer should do before
	 * entering the system
	 * @param username
	 * @param password
	 * @param id
	 * @param userType - OSH Or Dept. employee
	 * @return true if the login succeeded, false otherwise
	 */
	public boolean loginDept(String username , String password , String id,String userType){
		return _bridge.loginDept(username , password,id,userType);
	}

	/**
	 * The login method that each user "OSH" representer should do before
	 * entering the system
	 * @param username
	 * @param password
	 * @param id
	 * @param userType - OSH Or Dept. employee
	 * @return true if the login succeeded, false otherwise
	 */
	public boolean loginOsh(String username , String password , String id,String userType){
		return _bridge.loginOsh(username , password,id,userType);
	}

	/**
	 * This is the method that will run after the dept. employee enters his request for automating
	 * a form
	 * @param depId
	 * @param formName
	 * @return true if the filling succeeded , false otherwise
	 */
	public boolean fillForm(int depId, String formName){
		return _bridge.fillForm(depId, formName);
	}

	/**
	 * View automating request by name. This will be done after the OSH representer will choose
	 * his request.
	 * @param name
	 * @return the set of results , according to the name
	 */
	public Set viewByName(String name){
		return _bridge.viewByName(name);
	}

	/**
	 * View automating request by category. This will be done after the OSH representer will choose
	 * his request.
	 * @param name
	 * @return the set of results , according to the category
	 */
	public Set viewByCategory(String category){
		return _bridge.viewByCategory(category);
	}

	/**
	 * View automating request by number. This will be done after the OSH representer will choose
	 * his request.
	 * @param name
	 * @return set of results according to the number
	 */
	public Set viewByNumber(int number){
		return _bridge.viewByNumber(number);
	}

	/**
	 * setter to the bridge
	 * @param bridge
	 */
	public static void setbridge(ForumBridge bridge) {
		_bridge = bridge;
	}

}
