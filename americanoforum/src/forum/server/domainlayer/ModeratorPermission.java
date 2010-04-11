/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package forum.server.domainlayer;

/**
 * A class that represents a user permission of the type "moderator"
 */
public class ModeratorPermission implements UserPermission{
    private static ModeratorPermission instance=null;

    /**
  * approve to adds a new message as moderator
  * @param aSbj - he subject of the message
  * @param aCont - the content of the message
  */
    public void addMessage(String aSbj, String aCont) {
        return;
    }

    /**
 * approve to modify a new message as logged in user
 * @param aMsg - the message to modify
 * @param aCont - the  new content
 */
    public void modifyMessage(User aUsr, Message aMsg, String aCont) {
        return;
    }

    /**
 * view a message
 * @param aMsg - the message
 */
    public void viewMessage(Message aMsg) {
        return;
    }

    /**
 * approve to reply to a message as a logged in  message
 * @param aParent_msg - the parent message
 * @param aSbj - the subject
 * @param aCont - the content
  */
    public void reply(Message aParent_msg, String aSbj, String aCont) {
        return;
    }

    /**
 * get the instance
 * @return the user pwemission
 */
	public static UserPermission getInstance() {
		if (getInstanceField()==null){
                    setInstance(new ModeratorPermission());
                    return getInstanceField();
                }
                else
                    return getInstanceField();
	}

/**
 * get the instance
 * @return the guest permission
 */
    public static ModeratorPermission getInstanceField() {
        return instance;
    }
/**
 * sets the instance of the permission
 * @param instance - the new insance
 */
    public static void setInstance(ModeratorPermission instance) {
        ModeratorPermission.instance = instance;
    }

    /**
     * approves deleting a message as a logged-in user
     * @param msg the message to delete
     */
    public void deleteMessage(Message msg) {
        return;
    }

    /**
     * approves granting moderator permissions
     * @param tUsr
     */
    public void changeToModerator(User tUsr) {
        throw new UnsupportedOperationException();
    }

}
