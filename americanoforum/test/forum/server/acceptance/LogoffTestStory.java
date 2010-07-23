package forum.server.acceptance;



/**
 * Class dedicated to test the View Story (second use case)
 */
public class LogoffTestStory extends TestForumProject{

	public LogoffTestStory() {
		super();
	}

	/**
	 * Tests the login operation that is needed before entering the system.
	 */
	public void testLogoff(){
                assertFalse(logoff());
                 assertTrue(login("iluxa13", "abcd"));
                assertTrue(logoff());
                assertFalse(logoff());
                assertFalse(addFictiveMessage());
                
	}

}
