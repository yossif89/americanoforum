/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package forum.server.domainlayer;

/**
 *
 * @author Yossi
 */
public class AdminPermission implements UserPermission{
    private static AdminPermission instance=null;
    
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

    public void deleteMessage(Message msg) {
        return;
    }

    public void changeToModerator(User tUsr) {
        return;
    }

    /**
 * get the instance
 * @return the user pwemission
 */
	public static UserPermission getInstance() {
		if (getInstanceField()==null){
                    setInstance(new AdminPermission());
                    return getInstanceField();
                }
                else
                    return getInstanceField();
	}

/**
 * get the instance
 * @return the guest permission
 */
    public static AdminPermission getInstanceField() {
        return instance;
    }
/**
 * sets the instance of the permission
 * @param instance - the new insance
 */
    public static void setInstance(AdminPermission instance) {
        AdminPermission.instance = instance;
    }

}
