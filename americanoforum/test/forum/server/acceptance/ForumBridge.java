package forum.server.acceptance;



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
	public boolean register( String username, String password, String first, String last, String email, String address, String gender);

        public void disconect();

         /**
	 * The logoff method that enables a logged in user to exit the system and maybe login as a different user
	 * entering the system
	 * @return true if the logoff  succeeded, false otherwise
	 */
    public boolean logoff();

    /**Adding a fictive message to the system to check if this option is enabled
     * @return true if the logoff  succeeded, false otherwise
     **/
    public boolean addFictiveMessage();
}
