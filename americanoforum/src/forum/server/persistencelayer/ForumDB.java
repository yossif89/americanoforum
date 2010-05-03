/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package forum.server.persistencelayer;

import java.util.List;

/**
 *
 * @author Yossi
 */
public class ForumDB {

    private String forumName;
    private int msgIndex;
    private List<UserDB> allUsers;
    private List<MessageDB> allMsgs;

    public String getForumName() {
        return forumName;
    }

    public void setForumName(String forumName) {
        this.forumName = forumName;
    }

    public List<MessageDB> getAllMsgs() {
        return allMsgs;
    }

    public void setAllMsgs(List<MessageDB> allMsgs) {
        this.allMsgs = allMsgs;
    }

    public List<UserDB> getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(List<UserDB> allUsers) {
        this.allUsers = allUsers;
    }

    public int getNumOfMsgs() {
        return msgIndex;
    }

    public void setNumOfMsgs(int msgIndex) {
        this.msgIndex = msgIndex;
    }
}
