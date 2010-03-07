package forum.server.domainlayer;

public class GuestPermission implements UserPermission {

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

	public UserPermission getInstance() {
		throw new UnsupportedOperationException();
	}
}