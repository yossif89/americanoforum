package forum.server.domainlayer;

import java.util.HashMap;
import java.util.Vector;

public class Forum {
	HashMap<Integer, Message> _messages = new HashMap<Integer,Message>();
	Vector<User> _registered = new Vector<User>();
	Vector<User> _online_users = new Vector<User>();

	public User login(String aUsername, String aPass) {
		throw new UnsupportedOperationException();
	}

	public void logoff(User aUser) {
		throw new UnsupportedOperationException();
	}

	public void register(String aUsername, String aPass, String aEmail, String aFirstName, String aLastName, String aAddress, String aGender) {
		throw new UnsupportedOperationException();
	}
}