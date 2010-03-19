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
public class AddMessageMessage implements ClientMessage{
String _subject;
String _message;
String _user;
    public AddMessageMessage(String subject, String message,String u) {
        this._subject = subject;
        this._message = message;
        this._user=u;
    }

   

    public ServerResponse doOperation(ForumFacade ff) {
      return  ff.addMessage(_subject, _message, _user);
    }

}
