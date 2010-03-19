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
public class LogoffMessage implements ClientMessage{
String _user;
    public LogoffMessage(String user) {
      this._user=user;
    }
    public ServerResponse doOperation(ForumFacade ff) {
            return ff.logoff(_user);
        }
}
