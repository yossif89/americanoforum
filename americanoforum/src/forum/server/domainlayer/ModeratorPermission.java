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

    public void addMessage(String aSbj, String aCont) {
        return;
    }

    public void modifyMessage(User aUsr, Message aMsg, String aCont) {
        return;
    }

    public void viewMessage(Message aMsg) {
        return;
    }

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

    public void deleteMessage(Message msg) {
        return;
    }

    public void changeToModerator(User tUsr) {
        throw new UnsupportedOperationException();
    }

}
