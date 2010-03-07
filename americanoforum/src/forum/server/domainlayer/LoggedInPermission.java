package forum.server.domainlayer;

public class LoggedInPermission implements UserPermission {
    
 private static LoggedInPermission instance=null;

	public void addMessage(String aSbj, String aCont) {
            return;
	}

	public void modifyMessage(Message aMsg, String aCont) {
		return;
	}

	public void viewMessage(Message aMsg) {
	    return;
	}

	public void reply(Message aParent_msg, String aSbj, String aCont) {
		return;
	}

	public static UserPermission getInstance() {
		if (getInstanceField()==null){
                    setInstance(new LoggedInPermission());
                    return getInstanceField();
                }
                else
                    return getInstanceField();
	}

    public static LoggedInPermission getInstanceField() {
        return instance;
    }

    public static void setInstance(LoggedInPermission instance) {
        LoggedInPermission.instance = instance;
    }
}