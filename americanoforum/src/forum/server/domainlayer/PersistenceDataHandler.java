/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package forum.server.domainlayer;

import java.util.Date;

/**
 *
 * @author Yossi
 */
public interface PersistenceDataHandler {

    Forum getForumFromXml();
    void addRegUserToXml(String username, String password, String email, String firstname, String lastname, String address, String gender, String up);
    void addMsgToXml(String sbj, String cont, long msg_id, long parent_id, String username, Date datetime);
    void modifyMsgInXml(long id_toChange, String newCont);
    void changeUserPermission(String username, String permission);
    void deleteMsgFromXml(long msg_id);

}
