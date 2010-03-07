package forum.server.domainlayer;

import java.util.HashMap;
import java.util.Vector;

public class User {
	UserPermission _up;
	Details _details;
	HashMap<Integer, Message> _myMessages = new HashMap<Integer, Message>();

	public void addMessage(String aSbj, String aCont) {
		throw new UnsupportedOperationException();
	}

	public void modifyMessage(Message aMsg, String aCont) {
		throw new UnsupportedOperationException();
	}

	public void viewMessage(Message aMsg) {
		throw new UnsupportedOperationException();
	}

	public void reply(Message aParent_msg, String aSbj, String aCont) {
		throw new UnsupportedOperationException();
	}
}