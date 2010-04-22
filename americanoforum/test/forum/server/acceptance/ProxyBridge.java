package Tests;

import java.util.HashSet;
import java.util.Set;

/**
 * The proxy bridge that is used to simulate an implementation for the tests in case that
 * the implementation doesn't yet exists.
 */
public class ProxyBridge implements ForumBridge{

	//will get either null value , in case no real bridge exists , or
	//will get the real bridge in order to really test the program.
	public ForumBridge _bridge;

	public ProxyBridge(ForumBridge bridge) {
		this._bridge = bridge;
	}

	/**
	 * A method that helps test the viewByName operation. It returns a Set
	 * of fabricated results, according to the name given.
	 */
	@Override
	public Set viewByName(String name){
		Set a = new HashSet();
		a.add("student education form - Mayer Goldberg");
		if (_bridge==null){
			if ((name.equals(""))|(name.equals("Daniel Berend")))
				return new HashSet();
			else if (name.equals("Mayer Goldberg")) return a;
			return null;
		}
		else return _bridge.viewByName(name);
	}

	/**
	 * A method that helps test the viewByName operation. It returns a Set
	 * of fabricated results,according to the category.
	 */
	@Override
	public Set viewByCategory(String category){
		Set a = new HashSet();
		Set c = new HashSet();
		c.add("get cable form - Igal Meital");
		c.add("get pic18 form - Igal Meital");
		a.add("student failure form - Mayer Algem");
		if (_bridge==null){
			if ((category.equals(""))|(category.equals("Exams")))
				return new HashSet();
			if (category.equals("Miscellaneous"))
				return a;
			if (category.equals("Equipment"))
				return c;
			return null;
		}
		else return _bridge.viewByCategory(category);
	}

	/**
	 * A method that helps test the viewByNumber operation. It returns a Set
	 * of fabricated results, according to the number.
	 */
	@Override
	public Set viewByNumber(int number){
		Set c = new HashSet();
		c.add("get cable form - Igal Meital");
		c.add("get pic18 form - Igal Meital");
		if (_bridge==null){
			if ((number<0) |( number == 3))
				return new HashSet();
			if (number == 2)
				return c;
		}
		else return _bridge.viewByNumber(number);
		return null;
	}


	@Override
	public boolean fillForm(int depId, String formName){
		// TODO Auto-generated method stub
		if (_bridge==null){
			if (depId<0 | formName.equals(""))
				return false;
			return depId==1;
		}
		else return _bridge.fillForm(depId,formName);
	}

	@Override
	public boolean loginDept(String username , String password , String id,String userType) {
		// TODO Auto-generated method stub
		if (_bridge==null){
			return (username.equals("Hila"))& (password.equals("1111")) & (id.equals("123")) & (userType.equals("DeptUser")) ;
		}
		else return _bridge.loginDept(username, password,id,userType);
	}

	@Override
	public boolean loginOsh(String username , String password , String id,String userType) {
		// TODO Auto-generated method stub
		if (_bridge==null){
			return (username.equals("Ilya"))& (password.equals("2222")) & (id.equals("456")) & (userType.equals("OshUser")) ;
		}
		else return _bridge.loginOsh(username, password,id,userType);
	}




}
