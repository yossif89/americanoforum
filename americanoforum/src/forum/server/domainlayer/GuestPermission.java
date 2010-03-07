package forum.server.domainlayer;

public class GuestPermission implements UserPermission {
 private static GuestPermission instance=null;



	public void addMessage(String aSbj, String aCont) {
		throw new UnsupportedOperationException();
	}

	public void modifyMessage(Message aMsg, String aCont) {
		throw new UnsupportedOperationException();
	}

	public void viewMessage(Message aMsg) {
	    return;
	}

	public void reply(Message aParent_msg, String aSbj, String aCont) {
		throw new UnsupportedOperationException();
	}

	public static UserPermission getInstance() {
		if (getInstanceField()==null){
                    setInstance(new GuestPermission());
                    return getInstanceField();
                }
                else
                    return getInstanceField();
	}

    public static GuestPermission getInstanceField() {
        return instance;
    }

    public static void setInstance(GuestPermission instance) {
        GuestPermission.instance = instance;
    }

}
