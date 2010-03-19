/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package forum.tcpcommunicationlayer;

import forum.server.domainlayer.ForumFacade;
import forum.server.domainlayer.User;

/**
 *
 * @author Ilya
 */
public class LoginMessage implements ClientMessage{
String _username;
String _pass;
String _user;

    public LoginMessage(String user, String pass,String u) {
        this._username = user;
        this._pass = pass;
        this._user = u;
    }

 

    public ServerResponse doOperation(ForumFacade ff) {
       return   ff.login(_username, _pass);
    }

}
