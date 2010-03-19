/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package forum.tcpcommunicationlayer;


/**
 *
 * @author Ilya
 */
public  class ServerResponse implements  java.io.Serializable {

    private String _response;
    private Exception _ex;
    private boolean _executed;

    public ServerResponse() {
        this._response = "";
        this._executed = false;
        this._ex = null;
    }

    public String getResponse() {
        return _response;
    }

    public boolean hasExecuted() {
        return _executed;
    }


    public void setResponse(String _response) {
        this._response = _response;
        this._executed = true;
    }

    public void setEx(Exception _ex) {
        this._ex = _ex;
    }

    public Exception getEx() {
        return _ex;
    }




}
