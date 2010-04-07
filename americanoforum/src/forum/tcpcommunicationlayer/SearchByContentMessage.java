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
public class SearchByContentMessage implements ClientMessage {

    String _toSearch;
    int _from;
    int _to;

    public SearchByContentMessage(String toS, int from, int to){
        _toSearch=toS;
        _from=from;
        _to=to;
    }

    public ServerResponse doOperation(ForumFacade ff) {
        return ff.searchByContent(_toSearch, _from, _to);
    }
}
