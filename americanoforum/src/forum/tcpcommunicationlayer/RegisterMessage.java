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
public class RegisterMessage implements ClientMessage{
    String _userName;
    String _pass;
    String _mail;
    String _first;
    String _last;
     String _address;
     String _gender;
     String _u;

    public RegisterMessage(String _userName, String _pass, String _mail, String _first, String _last, String _address, String _gender, String u) {
        this._userName = _userName;
        this._pass = _pass;
        this._mail = _mail;
        this._first = _first;
        this._last = _last;
        this._address = _address;
        this._gender = _gender;
        this._u = u;

    }

 
    public ServerResponse doOperation(ForumFacade ff) {
        return ff.register(_u, _userName, _pass, _mail, _first, _last, _address, _gender);
    }

}
