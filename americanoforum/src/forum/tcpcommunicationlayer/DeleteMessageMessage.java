/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package forum.tcpcommunicationlayer;

import forum.server.domainlayer.ForumFacade;

/**
 *
 * @author Ilya
 */
public class DeleteMessageMessage implements ClientMessage{
   int _idToDelete;
   String _user;

    public DeleteMessageMessage(int _idToDelete, String _user) {
        this._idToDelete = _idToDelete;
        this._user = _user;
    }


    public ServerResponse doOperation(ForumFacade ff) {
         return ff.deleteMessage(_idToDelete, _user);
    }


}
