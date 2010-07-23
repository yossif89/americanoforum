package forum.server.acceptance;



/**
 * Test dedicated for the first story of the filling form for automating.
 */
public class LoginTestStory extends TestForumProject{

	public LoginTestStory() {
		super();
	}

	/**
	 * Tests the login operation that is needed in order to fill a request.
	 */
	public void testLogin(){
                    assertTrue(login("iluxa13", "abcd"));
                    assertFalse(login("",""));
                    assertFalse(login("iluxa13",""));
                    assertFalse(login("","abcd"));
                    assertFalse(login("iluxa13","abcde"));
                    assertFalse(login("rostik","1234"));
                    assertFalse(login("iluxa14","abcd"));
                    //disconect();
                    logoff();
                    
	}



}
