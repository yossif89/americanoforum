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
public class SearchMessage implements ClientMessage{
        String _toSearch;
        String _user;

    public SearchMessage(String _toSearch, String _user) {
        this._toSearch = _toSearch;
        this._user = _user;
    }

    public ServerResponse doOperation(ForumFacade ff) {
        throw new UnsupportedOperationException("Not supported yet.");
    }




}
