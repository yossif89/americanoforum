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
public class ShowAllUsersMessage implements ClientMessage {


    public ServerResponse doOperation(ForumFacade ff) {
      return ff.encode_allUsers();
    }



}
