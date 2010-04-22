package forum.server.acceptance;


import java.util.Set;


/**
 * The interface of the tested operations
 */
public interface ForumBridge {

	/**
	 * The login method that each user  should do before
	 * entering the system
	 * @param username
	 * @param password
	 * @return true if the login succeeded, false otherwise
	 */
	public boolean login(String username , String password);


        /**
	 * The login method that each user  should do before
	 * entering the system
	 * @param username
	 * @param password
	 * @return true if the login succeeded, false otherwise
	 */
	public boolean login(String username , String password);


}
