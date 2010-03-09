package forum.server.domainlayer;

import java.util.HashMap;
import java.util.Vector;

public class User {
	private UserPermission _up;
	private Details _details;
	private HashMap<Integer, Message> _myMessages = new HashMap<Integer, Message>();

    public User() {
        this._up = GuestPermission.getInstance();
        this._details=null;
    }

        /**
         * Creates and adds the message to the db , creates a link between  the user and the message
         * @param aSbj the subject
         * @param aCont the content
         * @throws UnsupportedOperationException
         */
	public Message addMessage(String aSbj, String aCont)  throws UnsupportedOperationException{
               _up.addMessage(aSbj, aCont);
               Message m = new Message(aSbj, aCont, this);
               this._myMessages.put(m.getMsg_id(), m);
               return m;

	}
        /**
         * modifies an existing message only if the user is the creator.
         * @param aMsg the existing message
         * @param aCont the content
         * @throws UnsupportedOperationException
         */
	public void modifyMessage(Message aMsg, String aCont) throws UnsupportedOperationException {
		_up.modifyMessage(aMsg, aCont);
                if (this==aMsg.getCreator()){
                     aMsg.setContent(aCont);
                }
                else{
                    throw new UnsupportedOperationException();
                }
	}

        /**
         * Only checks if the user is authorized to view the message
         * @param aMsg the message
         * @throws UnsupportedOperationException
         */
	public void viewMessage(Message aMsg) throws UnsupportedOperationException {
		_up.viewMessage(aMsg);
	}
        /**
         * adds the reply to the parent message and adds the message to the users vector
         * @param aParent_msg
         * @param aSbj the subject
         * @param aCont the content
         * @throws UnsupportedOperationException
         */
	public Message reply(Message aParent_msg, String aSbj, String aCont)  throws UnsupportedOperationException{
	       _up.reply(aParent_msg, aSbj, aCont);
               Message m = new Message(aSbj, aCont, this);
               m.setParent(aParent_msg);
               this._myMessages.put(m.getMsg_id(), m);
               return m;
	}

        public Details getDetails() {
            return _details;
        }

        public void setDetails(Details _details) {
            this._details = _details;
        }

    public void setUp(UserPermission _up) {
        this._up = _up;
    }

    public HashMap<Integer, Message> getMyMessages() {
        return _myMessages;
    }

    public UserPermission getUp() {
        return _up;
    }
    

}