package Tests;



/**
 * Test dedicated for the first story of the filling form for automating.
 */
public class FillFormTestStory extends TestAutomatingProject{

	public FillFormTestStory() {
		super();
	}

	/**
	 * Tests the login operation that is needed in order to fill a request.
	 */
	public void testLogin(){
		//The correct data
		assertTrue(loginDept("Hila", "1111" , "034741736","DeptUser"));

		//variations on incorrect data:
		assertFalse(loginDept("Hila", "2222" , "123","DeptUser"));
		assertFalse(loginDept("Ilya", "1111" , "123","DeptUser"));
		assertFalse(loginDept("Hila", "1111" , "034741777","DeptUser"));
		assertFalse(loginDept("Hila", "1111" , "123","OshUser"));
	}

	/**
	 * Tests the fill form operation , done by the department employee
	 */
	public void testFillForm(){
		if (loginDept("Hila", "1111" , "123","DeptUser")){
		//the "repository" form belongs to department number 1
		assertTrue(fillForm(1,"repository"));
		//the "repository" form doesn't belong to department number 2
		assertFalse(fillForm(2,"repository"));
		}
		else{
			assertTrue(false);
		}

	}


}
