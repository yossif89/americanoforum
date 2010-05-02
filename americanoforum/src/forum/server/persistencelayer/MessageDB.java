/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package forum.server.persistencelayer;

import java.util.Date;

/**
 *
 * @author Yossi
 */
public class MessageDB {

    private long messageId;
    private String subject;
    private String content;
    private Date date;
    private MessageDB father;
    private UserDB creator;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserDB getCreator() {
        return creator;
    }

    public void setCreator(UserDB creator) {
        this.creator = creator;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public MessageDB getFather() {
        return father;
    }

    public void setFather(MessageDB father) {
        this.father = father;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


}
