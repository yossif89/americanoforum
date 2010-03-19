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
public class PromoteMessage implements ClientMessage{
    String _toPromote;
    String _u;

    public PromoteMessage(String _toPromote, String u) {
        this._toPromote = _toPromote;
        this._u = u;
    }

    public ServerResponse doOperation(ForumFacade ff) {
        return ff.promoteMessage(_u, _toPromote);
    }


}
