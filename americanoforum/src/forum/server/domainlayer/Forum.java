package forum.server.domainlayer;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Forum {
        static Logger logger = Logger.getLogger("americanoforum");
	HashMap<Integer, Message> _messages = new HashMap<Integer,Message>();
	HashMap<String, User> _registered = new HashMap<String, User>();
	HashMap<String, User> _online_users = new HashMap<String, User>();
       PersistenceDataHandler pipe = new PersistenceDataHandlerImpl();


    /**
	 * A function that receives a password in String representation and returns
	 * the encryption of the password in hex representation.
	 *
	 * @param password The password which we want to encrypt
	 * @return the encrypted password in hex representation
	 * @throws NoSuchAlgorithmException MD5 is not supported by this version of java
	 */
	public static String encryptPassword(String password) throws NoSuchAlgorithmException {
		String encryptedPassword = "";

		byte[] b = password.getBytes();
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(b);
		b = md.digest();

		for (int i = 0; i < b.length; i++) {
			encryptedPassword += String.format("%02x",0xFF & b[i]);
		}

		return encryptedPassword;
	}

    /**
     * sets a collection (hash map)  of messages in the forum
     * @param msgs
     */
        public void setMessages(HashMap<Integer, Message> msgs){
            this._messages=msgs;
        }
/**
 *  sets a collection (hash map)  ofregistered users in the forum
 * @param users
 */
        public void setRegistered(HashMap<String, User> users){
            this._registered=users;
        }
/**
 * adds a new message to the forum, the messsage will be added as a root message
 * @param aSbj - the subject of the mesage
 * @param aCont - the content of the message
 * @param aUsr - the user that adds the message
 */
        public void addMessage (String aSbj,String aCont , User aUsr) throws Exception{
            try{
           Message tMsg =  aUsr.addMessage(aSbj,aCont);
           _messages.put(tMsg.getMsg_id(), tMsg);
           Message.incId();
           pipe.addMsgToXml(aSbj, aCont, tMsg.getMsg_id(), -1, aUsr.getDetails().getUsername(), tMsg.getDate());
            }
            catch(Exception e){
            Forum.logger.log(Level.FINE, "Forum:couldn't add a message with the header : "+aSbj);
            throw e;
            }
        }

        public void modifyMessage(Message aMsg, String aNewCont, User aUsr){
            aUsr.modifyMessage(aMsg, aNewCont);
            pipe.modifyMsgInXml(aMsg.getMsg_id(), aNewCont);
        }

/**
 * adds a new reply to a message in the forum
 * @param aSbj- the subject of the mesage
 * @param aCont-  the content of the message
 * @param aUsr- the user that adds the message
 * @param parent - the parent message
 */
        public void addReply(String aSbj,String aCont,User aUsr, Message parent) throws Exception{
            try{
                Message tMsg =  aUsr.addMessage(aSbj,aCont);
                Message.incId();
                tMsg.setParent(parent);
                parent.getChild().add(tMsg);
                pipe.addMsgToXml(aSbj, aCont,tMsg.getMsg_id(), parent.getMsg_id(), aUsr.getDetails().getUsername(), tMsg.getDate());
              
            }
            catch(Exception e){
                 Forum.logger.log(Level.FINE, "Forum:couldn't add a reply  message with the header : "+aSbj);
                 throw e;
            }

            }
        

        public void deleteMessage(Message msg, User tUsr){
            tUsr.deleteMessage(msg);
            if (msg.getParent() == null){
                this._messages.remove(msg.getMsg_id());
            }
            else{
                msg.getParent().getChild().remove(msg);
            }
        }


/**
 * adds a new user as a register user
 * @param aUser - the user we want to add
 */
        public void addToRegistered (User aUser){
            Forum.logger.log(Level.INFO, "Forum:is adding a new registered user : "+aUser.getDetails().getUsername());
            this._registered.put(aUser.getDetails().getUsername(),aUser);
        }
/**
 * adds a new user as a on line user
 * @param aUser -the user we want to add
 */
         public void addToOnline (User aUser){
            Forum.logger.log(Level.INFO, "Forum: registered user : "+aUser.getDetails().getUsername() +"is online!");
            this._online_users.put(aUser.getDetails().getUsername(),aUser);
        }
/**
 * log off a user
 * @param aUser - the user
 */
         public void turnOffline(User aUser){
             Forum.logger.log(Level.INFO, "Forum: registered user : "+aUser.getDetails().getUsername() +"is offline!");
             this._online_users.remove(aUser);
         }

/**
 * gets the on line users
 * @return - a hash map of on line users
 */
        public HashMap<String, User> getOnlineUsers(){
            return this._online_users;
        }
/**
 * gets the registerd users
 * @return - a hash map of registerd users
 */
        public HashMap<String, User> getRegisteredUsers(){
            return this._registered;
        }
 /**
  * .gets the root messages
  * @return a hash map with the root messages
  */
        public HashMap<Integer, Message> getMessages(){
            return this._messages;
        }
        /**
         * In case the username exists , and it fits the password , the username instance is being added to the onlineusers
         * in any other case , an exception is thrown
         * @param aUsername
         * @param aPass
         * @return the user that logged in
         * @throws IllegalAccessError
         */
	public User login(String aUsername, String aPass) throws IllegalAccessError {
	   User tUsr = this._registered.get(aUsername);
           if (tUsr==null){
               Forum.logger.log(Level.SEVERE,"Forum: unregistered user trying to login");
               throw new IllegalAccessError();
           }
           String encryptedPass="";
            try {
                encryptedPass = this.encryptPassword(aPass);
            } catch (NoSuchAlgorithmException ex) {
                //Logger.getLogger(Forum.class.getName()).log(Level.SEVERE, null, ex);
            }
           if ( !tUsr.getDetails().getPassword().equals(encryptedPass)){
               Forum.logger.log(Level.SEVERE,"Forum: unregistered user" + aUsername +" entered unvalid password");
               throw new IllegalAccessError();
           }
           this._online_users.put(aUsername, tUsr);
           tUsr.setUp(LoggedInPermission.getInstance());
           Forum.logger.log(Level.INFO, "Forum: registered user : "+aUsername+"is logged in and online");
          return tUsr;
	}

        /**
         * assumes that the user is in the logged in db , and removes it from that list
         * @param aUser
         */
	public void logoff(User aUser) {
		this._online_users.remove(aUser.getDetails().getUsername());
                aUser.setUp(GuestPermission.getInstance());
               Forum.logger.log(Level.INFO, "Forum: registered user : "+aUser.getDetails().getUsername()+"has logged off");
	}

        /**
         * creates an instance of the users details from the given data , and adds it to the owner user given
         * in addition it changes the users permission to logged in permission
         * and adds the user to the relevant to dbs: logged-in , registers.
         * @param aUsername
         * @param aPass
         * @param aEmail
         * @param aFirstName
         * @param aLastName
         * @param aAddress
         * @param aGender
         */
	public void register(User aUsr,String aUsername, String aPass, String aEmail, String aFirstName, String aLastName, String aAddress, String aGender)  throws Exception{
                String encryptedPass="";
                try {
                    encryptedPass = this.encryptPassword(aPass);
                } catch (NoSuchAlgorithmException ex) {
                 Forum.logger.log(Level.FINE, "no such algorithm: "+ ex.toString());
                }
                try{
                Details d = new Details(aUsername, encryptedPass, aEmail, aFirstName, aLastName, aAddress, aGender);
                aUsr.setDetails(d);
                aUsr.setUp(LoggedInPermission.getInstance());
                this._online_users.put(aUsername, aUsr);
                this._registered.put(aUsername, aUsr);
                pipe.addRegUserToXml(aUsername, encryptedPass, aEmail, aFirstName, aLastName, aAddress, aGender,"LoggedInPermission");
                 Forum.logger.log(Level.INFO, "Forum: guest user : "+aUsr.getDetails().getUsername()+" registered successfuly");
              }
              catch(Exception e){
                     Forum.logger.log(Level.FINE, "Forum: problem registering : " + aUsr.getDetails().getUsername() + ": " + e.toString());
                     throw e;
              }
	}

        public void changeToModerator(User curr_user, User to_change){
            curr_user.changeToModerator(to_change);
            pipe.changeUserPermission(to_change.getDetails().getUsername(), "ModeratorPermission");
        }

    @Override
    public String toString(){
        String ans="";
        ans = ans+ "There are: "+ this._online_users.size() + " online users: \n";
        for(User user : this._online_users.values()){
            ans = ans+user.getDetails().getUsername()+ " ";
        }
        ans = ans + "\nSubjects: \n";
        for (Message msg : this._messages.values()){
               ans = ans + msg.getSubject() + "  msg_id:" + msg.getMsg_id() + "\n";
        }
        return ans;
    }
}//class
