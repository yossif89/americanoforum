package forum.server.acceptance;



/**
 * Class dedicated to test the View Story (second use case)
 */
public class RegisterTestStory extends TestForumProject{

	public RegisterTestStory() {
		super();
	}

	/**
	 * Tests the login operation that is needed before entering the system.
	 */
	public void testRegister(){
                assertTrue(register("felberba", "abcde", "yos", "fel", "@", "kiryat yam", "male"));
                assertFalse(register("iluxa13", "abcde", "yos", "fel", "@", "kiryat yam", "male"));
                assertFalse(register("felberba", "abcde", "yos", "fel", "@", "kiryat yam", "male"));
                //disconect();
                logoff();
	}

}
