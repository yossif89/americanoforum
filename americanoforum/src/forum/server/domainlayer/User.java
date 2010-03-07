package forum.server.domainlayer;

import java.util.HashMap;
import java.util.Vector;

public class User {
	private UserPermission _up;
	private Details _details;
	private HashMap<Integer, Message> _myMessages = new HashMap<Integer, Message>();

    public User() {
        this._up = GuestPermission.getInstance();
        this._details=null;
    }

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

        public Details getDetails() {
        return _details;
    }

    public void setDetails(Details _details) {
        this._details = _details;
    }

    public void setUp(UserPermission _up) {
        this._up = _up;
    }

    public HashMap<Integer, Message> getMyMessages() {
        return _myMessages;
    }

    public UserPermission getUp() {
        return _up;
    }
    

}