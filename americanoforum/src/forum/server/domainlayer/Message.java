package forum.server.domainlayer;

import java.util.Date;
import java.util.Vector;

public class Message {
	private int _msg_id;
	private String _subject;
	private String _content;
	private Date _date;
	Message _parent;
	Vector<Message> _child = new Vector<Message>();
	User _creator;
}