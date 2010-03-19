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

public class ModifyMessageMessage implements ClientMessage{
     int _id;
    String _message;
     String _u;

    public ModifyMessageMessage(int _id, String _message, String _u) {
        this._id = _id;
        this._message = _message;
        this._u = _u;
    }


    public ServerResponse doOperation(ForumFacade ff) {
        return ff.modifyMessage(_id, _message, _u);
    }

}
