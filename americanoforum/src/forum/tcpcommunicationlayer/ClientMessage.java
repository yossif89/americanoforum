/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forum.tcpcommunicationlayer;

import forum.server.domainlayer.ForumFacade;
import java.io.Serializable;



/**
 *
 * @author Ilya
 */
public interface ClientMessage extends Serializable {
    
    ServerResponse doOperation (ForumFacade ff);

}
