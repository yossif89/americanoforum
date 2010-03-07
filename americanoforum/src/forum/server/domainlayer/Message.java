package forum.server.domainlayer;

import java.util.Date;
import java.util.Vector;

public class Message {
        private static Integer gensym = 0;
	private int _msg_id;
	private String _subject;
	private String _content;
	private Date _date;
	Message _parent;
	Vector<Message> _child = new Vector<Message>();
	User _creator;

    public Message(String _subject, String _content, User _creator) {
        this._date = new Date();
       this._msg_id= Message.gensym.intValue();
        this._subject = _subject;
        this._content = _content;
        this._creator = _creator;
        this._parent=null;
    }

    public static void incId(){
        gensym += 1;
    }

    public Vector<Message> getChild() {
        return _child;
    }

    public String getContent() {
        return _content;
    }

    public User getCreator() {
        return _creator;
    }

    public Date getDate() {
        return _date;
    }

    public int getMsg_id() {
        return _msg_id;
    }

    public Message getParent() {
        return _parent;
    }

    public String getSubject() {
        return _subject;
    }

    public static Integer getGensym() {
        return gensym;
    }

    public void setChild(Vector<Message> _child) {
        this._child = _child;
    }

    public void setContent(String _content) {
        this._content = _content;
    }

    public void setCreator(User _creator) {
        this._creator = _creator;
    }

    public void setDate(Date _date) {
        this._date = _date;
    }

    public void setMsg_id(int _msg_id) {
        this._msg_id = _msg_id;
    }

    public void setParent(Message _parent) {
        this._parent = _parent;
    }

    public void setSubject(String _subject) {
        this._subject = _subject;
    }

    public static void setGensym(Integer gensym) {
        Message.gensym = gensym;
    }
    

}