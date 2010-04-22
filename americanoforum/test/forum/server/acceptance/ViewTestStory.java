package Tests;


import java.util.HashSet;
import java.util.Set;


/**
 * Class dedicated to test the View Story (second use case)
 */
public class ViewTestStory extends TestAutomatingProject{

	public ViewTestStory() {
		super();
	}

	/**
	 * Tests the login operation that is needed before entering the system.
	 */
	public void testLogin(){
		//The correct data
		assertTrue(loginOsh("Ilya", "2222" , "307011049","OshUser"));

		//variations on incorrect data:
		assertFalse(loginOsh("Ilya", "2222" , "034741736","OshUser"));
		assertFalse(loginOsh("Ilya", "1111" , "307011049","OshUser"));
		assertFalse(loginOsh("Ilya", "2222" , "307011049","DeptUser"));
		assertFalse(loginOsh("Hila", "2222" , "307011049","OshUser"));
	}

	/**
	 * Tests the operation of viewing results by giving a name.
	 */
	public void testViewByName(){
		Set a = new HashSet();
		Set b = new HashSet();
		a.add("student education form - Mayer Goldberg");
		//The correct data
		assertEquals(viewByName("Mayer Goldberg"),a);
		assertEquals(viewByName("Daniel Berend"),b);
	}

	/**
	 * Tests the operation of viewing results by giving a category.
	 */
	public void testByCategory(){
		Set a = new HashSet();
		Set b = new HashSet();
		Set c = new HashSet();
		a.add("student failure form - Mayer Algem");
		c.add("get cable form - Igal Meital");
		c.add("get pic18 form - Igal Meital");
		//The correct data
		assertEquals(viewByCategory("Miscellaneous"),a);
		assertEquals(viewByCategory("Exams"),b);
		assertEquals(viewByCategory("Equipment"),c);
	}

	/**
	 * Tests the operation of viewing results by giving a number of requests.
	 */
	public void testByNumber(){
		Set b = new HashSet();
		Set c = new HashSet();

		c.add("get cable form - Igal Meital");
		c.add("get pic18 form - Igal Meital");
		//The correct data
		assertEquals(viewByNumber(2),c);
		assertEquals(viewByNumber(3),b);
		assertEquals(viewByNumber(-1), b);
	}


}
