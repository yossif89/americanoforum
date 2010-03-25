package forum.server.domainlayer;

import java.util.logging.Level;

/**
 * A class that represents a user permission of the type "guest"
 */
public class GuestPermission implements UserPermission {
 private static GuestPermission instance=null;

 /**
  * approve to add a new message as guest user
  * @param aSbj - he subject of the message
  * @param aCont - the content of the message
  * @throws exception  because its not allowed for a guest user
  */
	public void addMessage(String aSbj, String aCont) {
		throw new UnsupportedOperationException();
	}
/**
 * approve to modify a new message as guest user
 * @param aMsg - the message to modify
 * @param aCont - the  new content
 * @throws exception because its not allowed for a guest user
 */
	public void modifyMessage(User aUsr, Message aMsg, String aCont) {
                    Forum.logger.severe("Guest Permission: the user isn't the creator of thus message");
		throw new UnsupportedOperationException();
	}
/**
 * view a message
 * @param aMsg - the message
 */
	public void viewMessage(Message aMsg) {
	    return;
	}
/**
 * approve to reply to a message as a guest message
 * @param aParent_msg - the parent message
 * @param aSbj - the subject
 * @param aCont - the content
 * @throws exception  because its not allowed for a guest user
  */
	public void reply(Message aParent_msg, String aSbj, String aCont) {
		throw new UnsupportedOperationException();
	}
/**
 * get the instance
 * @return the user pwemission
 */
	public static UserPermission getInstance() {
		if (getInstanceField()==null){
                    setInstance(new GuestPermission());
                    return getInstanceField();
                }
                else
                    return getInstanceField();
	}
/**
 * get the instance
 * @return the guest permission
 */
    public static GuestPermission getInstanceField() {
        return instance;
    }
/**
 * sets the instance of the permission
 * @param instance - the new insance
 */
    public static void setInstance(GuestPermission instance) {
        GuestPermission.instance = instance;
    }

    public void deleteMessage(Message msg) {
        throw new UnsupportedOperationException();
    }

    public void changeToModerator(User tUsr) {
        throw new UnsupportedOperationException();
    }

}//class
