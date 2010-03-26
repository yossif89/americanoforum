/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package forum.tcpcommunicationlayer;

import forum.server.domainlayer.ForumFacade;

/**
 *
 * @author Yossi
 */
public class SearchByAuthorMessage implements ClientMessage{

    String _authorUserName;
    int _from;
    int _to;

    public SearchByAuthorMessage(String authorUS, int fromInd, int toInd){
        this._authorUserName = authorUS;
        this._from = fromInd;
        this._to = toInd;
    }

    public ServerResponse doOperation(ForumFacade ff) {
        return ff.searchByAuthor(_authorUserName, _from, _to);
    }


}
