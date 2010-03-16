/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package forum.server.domainlayer;

public class PermissionFactory {

    public static UserPermission getUserPermission(String permission){
        if (permission.equals("GuestPermission")){ //unnecessary - for generalization only!
            return GuestPermission.getInstance();
        }
        else if (permission.equals("LoggedInPermission")){
            return LoggedInPermission.getInstance();
        }
        else if (permission.equals("ModeratorPermission")){
            return ModeratorPermission.getInstance();
        }
        else if (permission.equals("AdminPermission")){
            return AdminPermission.getInstance();
        }
        else return null;

    }
}//class
