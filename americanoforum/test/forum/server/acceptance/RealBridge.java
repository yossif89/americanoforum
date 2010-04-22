package Tests;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

//There is no real implementation yet so no real connection making was possible!
//The class is build for compiling only.

import Impl.Category;
import Impl.Request;
import Impl.Systemi;
/**
 * This class Is simulating the case in which a real implementation exists ,
 * and if so , this real bridge connects the real implementation of the project to the
 * user acceptance tests.
 */
public class RealBridge implements ForumBridge {

	private Systemi sys ;

	public RealBridge(){
		sys = new Systemi();
		build();

	}

	public void build(){
		sys.addUser("Hila", "Hila", "1111", "034741736", "DeptUser");
		sys.addUser("Hila", "Hila", "1111", "123", "DeptUser" );
		sys.addUser("Ilya", "Ilya", "2222", "307011049", "OshUser");
		sys.addUser("Mayer Goldberg", "may", "9999", "111111111", "DeptUser");
		sys.addUser("Igal Meital", "igi", "8888", "22222222", "DeptUser");
		sys.addUser("Mayer Algem", "may2", "7777", "33333333", "DeptUser");
		sys.addFormToDept(1, "repository", "Mlai");
		Category.getInstance("Mlai");
	}

//The explanations(javadoc) for each method exists in the interface AutomatingFormBridge


	public boolean loginDept(String username , String password , String id,String userType){
		return sys.login(username, password, id, userType);
	}

	public boolean loginOsh(String username , String password , String id,String userType){
		return sys.login(username, password, id, userType);
	}

	public boolean fillForm(int depId, String formName){
		return sys.fillForm(depId, "repository");
	}

	public Set viewByName(String name){
		sys = new Systemi();
		Category.eraseAll();
		build();

		Set<String> toRet = new HashSet<String>();
		sys.login("may", "9999", "111111111", "DeptUser");
		Category.getInstance("Education");
		sys.addFormToDept(700, "student education form", "Education");
		sys.fillForm(700, "student education form");

		Set<Request> temp = sys.viewByName(name);

		for (Request r : temp){
			toRet.add(r.getName()+" - "+r.getInititator().getName());
		}


		return toRet;
	}

	public Set viewByCategory(String category){
		sys = new Systemi();
		Category.eraseAll();
		build();

		Set<String> toRet = new HashSet<String>();
		sys.login("may2", "7777", "33333333", "DeptUser");
		Category.getInstance("Miscellaneous");
		sys.addFormToDept(707, "student failure form", "Miscellaneous");
		sys.fillForm(707, "student failure form");

		sys.login("igi", "8888", "22222222", "DeptUser");
		Category.getInstance("Equipment");
		sys.addFormToDept(708, "get cable form", "Equipment");
		sys.addFormToDept(708, "get pic18 form", "Equipment");
		sys.fillForm(708, "get cable form");
		sys.fillForm(708, "get pic18 form");
		Set<Request> temp = sys.viewByCategory(category);

		for (Request r : temp){
			toRet.add(r.getName()+" - "+r.getInititator().getName());
		}

		return toRet;
	}

	public Set viewByNumber(int number){
		sys = new Systemi();
		Category.eraseAll();
		build();
		sys.login("igi" , "8888", "22222222", "DeptUser");
		Category.getInstance("Equipment");
		sys.addFormToDept(708, "get cable form", "Equipment");
		sys.addFormToDept(708, "get pic18 form", "Equipment");
		sys.fillForm(708, "get cable form");
		sys.fillForm(708, "get pic18 form");

		Set<String> toRet = new HashSet<String>();
		Set<Request> temp = sys.viewByNumber(number);
		if (temp==null)
			return toRet;
		for (Request r : temp){
			toRet.add(r.getName()+" - "+r.getInititator().getName());
		}
		return toRet;
	}





}
