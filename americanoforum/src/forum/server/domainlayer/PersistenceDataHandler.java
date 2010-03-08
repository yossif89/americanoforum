/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package forum.server.domainlayer;

/**
 *
 * @author Yossi
 */
public interface PersistenceDataHandler {

    Forum getForumFromXml();
    void addRegUserToXml(String username, String password, String email, String firstname, String lastname, String address, String gender, String up);
    void addMsgToXml(String sbj, String cont, int msg_id, int parent_id);
    void modifyMsgInXml(int id_toChange, String newCont);

}
