package forum.server.acceptance;



import java.util.Set;

import junit.framework.TestCase;

/**
 * The father class of each test class we will write, it contains
 * the main methods of the tested operations.
 */
public class TestForumProject extends TestCase{
	//the bridge to the real implementation , or the proxy bridge that will
	//simulate the run in a case that a real bridge doesn't exist.
	private static ForumBridge _bridge;

	public TestForumProject() {
		super();
	}

        public static void setBridge(ForumBridge _bridge) {
                TestForumProject._bridge = _bridge;
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
	public boolean login(String username , String password ){
		return _bridge.login(username , password);
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
	public boolean register( String username, String password, String first, String last, String email, String address, String gender ){
		return _bridge.register( username,  password,  first,  last,  email,  address,  gender);
	}

	

}
