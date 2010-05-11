package forum.server.domainlayer;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableProperty;

/**
 * http://www.compass-project.org/docs/2.2.0/reference/html/core-osem.html
 * @author Tomer Heber
 */
@Searchable
public class Message implements Serializable{
        private static Long gensym = (long)0;

        @SearchableId
	private long _msg_id;

        
        @SearchableProperty (name = "subject")
	private String _subject;

        @SearchableProperty (name = "content")
	private String _content;

        @SearchableProperty (name = "allCont")
        private String _allWords;

        @SearchableProperty (name = "creator")
        private String username;

        @SearchableProperty (name = "date")
	private Date _date;

	Message _parent;

	Vector<Message> _child = new Vector<Message>();

        User _creator;

    /**
     * constractor
     * @param _subject -  the subject of the message
     * @param _content - the content of the message
     * @param _creator - the user that create the message
     */
    public Message(String _subject, String _content, User _creator) {
        this._date = new Date();
       this._msg_id= Message.gensym.longValue();
        this._subject = _subject;
        this._content = _content;
        this._creator = _creator;
        this._allWords = this._subject + " " + this._content;
        this.username = this._creator.getDetails().getUsername();
        this._parent=null;
    }

    public Message(){}

    /**
     * inc the msg id counter
     */
    public static void incId(){
        gensym += 1;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAllWords() {
        return _allWords;
    }

    public void setAllWords(String _allWords) {
        this._allWords = _allWords;
    }

    /**
 * gets the vector of all replys messages to the message
 * @return the replys messages as a vector of messages
 */
    public Vector<Message> getChild() {
        return _child;
    }
/**
 * gets the  content of the message
 * @return the string of content
 */
    public String getContent() {
        return _content;
    }
/**
 * gets the creator of the message
 * @return a user that create the message
 */
    public User getCreator() {
        return _creator;
    }

  
    public String getCreatorString(){
        return _creator.getDetails().getUsername();
    }
/**
 * gets the date of the message
 * @return the datetime of the message
 */
    public Date getDate() {
        return _date;
    }
/**
 * gets the message id of the message
 * @return the number as int
 */
    public long getMsg_id() {
        return _msg_id;
    }
/**
 * gets the parent of the message if the message is a reply
 * in case this is null the message is a root message
 * @return the parent message
 */
    public Message getParent() {
        return _parent;
    }
/**
 * gets the subject of the message
 * @return the subject as string
 */
    public String getSubject() {
        return _subject;
    }
/**
 * return the current counter of the message id
 * @return the counter
 */
    public static Long getGensym() {
        return gensym;
    }
/**
 * sets a replys vector for the message
 * @param _child - the reply vector
 */
    public void setChild(Vector<Message> _child) {
        this._child = _child;
    }
/**
 * sets the content of the message
 * @param _content - the new content
 */
    public void setContent(String _content) {
        this._content = _content;
    }
/**
 * sets the creator of the message
 * @param _creator - a user
 */
    public void setCreator(User _creator) {
        this._creator = _creator;
    }
/**
 * sets the date of the creation of the message
 * @param _date - datetime
 */
    public void setDate(Date _date) {
        this._date = _date;
    }
/**
 * set the message id of the message
 * @param _msg_id - the message id as int
 */
    public void setMsg_id(long _msg_id) {
        this._msg_id = _msg_id;
    }
/**
 * sets the parent of the message
 * @param _parent - the  parent message
 */
    public void setParent(Message _parent) {
        this._parent = _parent;
    }
/**
 * sets the subject of the message
 * @param _subject - a content for message as string
 */
    public void setSubject(String _subject) {
        this._subject = _subject;
    }
/**
 * sets the counter fo the message
 * @param gensym - the new counter
 */
    public static void setGensym(Long gensym) {
        Message.gensym = gensym;
    }
    
}//class