/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package forum.tcpcommunicationlayer;

import java.io.Serializable;

/**
 *
 * @author Ilya
 */
public class ServerSearchResponse extends ServerResponse implements Serializable{

    Object[] results;

    public ServerSearchResponse(Object[] results) {
        this.results = results;
    }

   

}
