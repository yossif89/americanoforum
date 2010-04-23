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
public class AddReplyMessage implements ClientMessage{
  long _id;
  String _subj;
  String _message;
  String _user;

    public AddReplyMessage(long id, String subj, String message,String u) {
        this._id = id;
        this._subj = subj;
        this._message = message;
        _user=u;
    }

    

    public ServerResponse doOperation(ForumFacade ff) {
        return ff.reply(_subj, _message, _user, _id);
    }

}
