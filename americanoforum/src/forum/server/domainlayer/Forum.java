package forum.server.domainlayer;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.compass.core.CompassHit;
/**
 * This class represents the main class of the forum, all the operations on the forum are done through this
 * class.
 * */
public class Forum {
        static Logger logger = Logger.getLogger("americanoforum");
	HashMap<Long, Message> _messages;
        HashMap<Long, Message> _allMessages;
	HashMap<String, User> _registered;
	HashMap<String, User> _online_users;
        SearchEngine _searchEng;
       PersistenceDataHandler pipe;

       public Forum(){
           _messages = new HashMap<Long,Message>();
         _allMessages = new HashMap<Long,Message>();
	 _registered = new HashMap<String, User>();
	 _online_users = new HashMap<String, User>();
         System.out.println("ktovet "+_allMessages);
         _searchEng = new SearchEngineImpl(_allMessages);
        pipe = new PersistenceDataHandlerDBImpl();
       }

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
        public void setMessages(HashMap<Long, Message> msgs){
            this._messages=msgs;
        }

    public void setAllMessages(HashMap<Long, Message> _allMessages) {
        this._allMessages = _allMessages;
    }

    public void updateSearchEngine(){

        Collection<Message> msgs = _allMessages.values();
        for(Message msg: msgs){
              System.out.println("i"+msg);
            this._searchEng.addData(msg);
        }
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
           _allMessages.put(tMsg.getMsg_id(), tMsg);
           _searchEng.addData(tMsg);
           pipe.addMsgToXml(aSbj, aCont, tMsg.getMsg_id(), -1, aUsr.getDetails().getUsername(), tMsg.getDate());
            }
            catch(Exception e){
            Forum.logger.log(Level.FINE, "Forum:couldn't add a message with the header : "+aSbj);
            throw e;
            }
        }

        /**
         * modifies only the content of the message with the given params
         * @param aMsg , the object that represents the message to be changed
         * @param aNewCont , the new content
         * @param aUsr , the user that initiated the modification
         */
        public void modifyMessage(Message aMsg, String aNewCont, User aUsr){
            this._searchEng.removeMessage(aMsg);
            aUsr.modifyMessage(aMsg, aNewCont);
            //exceptions!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            this._searchEng.addData(aMsg);
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
                tMsg.setParent(parent);
                parent.getChild().add(tMsg);
                _allMessages.put(tMsg.getMsg_id(), tMsg);
                this._searchEng.addData(tMsg);
                pipe.addMsgToXml(aSbj, aCont,tMsg.getMsg_id(), parent.getMsg_id(), aUsr.getDetails().getUsername(), tMsg.getDate());
            }
            catch(Exception e){
                 Forum.logger.log(Level.FINE, "Forum:couldn't add a reply  message with the header : "+aSbj);
                 throw e;
            }

            }
        
/**
 * recursive method that deletes the message and its replies
 * @param msg
 * @param tUsr
 */
        public void deleteMessage(Message msg, User tUsr){

            tUsr.deleteMessage(msg);
            Vector<Message> child = msg.getChild();
            
            for (int i=0; i<child.size(); i++){
                deleteMessage(child.elementAt(i), tUsr);
            }
            if (msg.getParent() == null){
                this._messages.remove(new Long(msg.getMsg_id()));
                _allMessages.remove(new Long(msg.getMsg_id()));
            }
            else{
                msg.getParent().getChild().remove(msg);
                _allMessages.remove(new Long(msg.getMsg_id()));
            }
            this._searchEng.removeMessage(msg);
            pipe.deleteMsgFromXml(msg.getMsg_id());
            System.out.println("histayem behzlaha");
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
        public HashMap<Long, Message> getMessages(){
            return this._messages;
        }

    public HashMap<Long, Message> getAllMessages() {
        return _allMessages;
    }

        
        /**
         * In case the username exists , and it fits the password , the username instance is being added to the onlineusers
         * in any other case , an exception is thrown
         * @param aUsername
         * @param aPass
         * @return the user that logged in
         * @throws IllegalAccessError
         */
	public User login(String aUsername, String aPass) throws IllegalAccessException {
	   User tUsr = this._registered.get(aUsername);
           if (tUsr==null){
               Forum.logger.log(Level.SEVERE,"Forum: unregistered user trying to login");
               throw new IllegalAccessException();
           }
           String encryptedPass="";
            try {
                encryptedPass = this.encryptPassword(aPass);
            } catch (NoSuchAlgorithmException ex) {
               Forum.logger.severe("no such algorithm for encryption!");
            }
           if ( !tUsr.getDetails().getPassword().equals(encryptedPass)){
               Forum.logger.log(Level.SEVERE,"Forum: registered user" + aUsername +" entered unvalid password "+encryptedPass+" =? " +tUsr.getDetails().getPassword());
               throw new IllegalAccessException();
           }
           this._online_users.put(aUsername, tUsr);
          // tUsr.setUp(LoggedInPermission.getInstance());
           Forum.logger.log(Level.INFO, "Forum: registered user : "+aUsername+"is logged in and online");
          return tUsr;
	}

        /**
         * assumes that the user is in the logged in db , and removes it from that list
         * @param aUser
         */
	public void logoff(User aUser)  {
                if (aUser.getUp() instanceof GuestPermission){
                   throw new UnsupportedOperationException();
                }
		this._online_users.remove(aUser.getDetails().getUsername());
        //        aUser.setUp(GuestPermission.getInstance());
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
                User tried = this.getRegisteredUsers().get(aUsername);
                if (tried!=null)
                    throw new IllegalArgumentException();
                Details d = new Details(aUsername, encryptedPass, aEmail, aFirstName, aLastName, aAddress, aGender);
                aUsr.setDetails(d);
                aUsr.setUp(LoggedInPermission.getInstance());
                this._online_users.put(aUsername, aUsr);
                this._registered.put(aUsername, aUsr);
                pipe.addRegUserToXml(aUsername, encryptedPass, aEmail, aFirstName, aLastName, aAddress, aGender,"LoggedInPermission");
                 Forum.logger.log(Level.INFO, "Forum: guest user : "+aUsr.getDetails().getUsername()+" registered successfuly");
              }
               catch(IllegalArgumentException e){
                     Forum.logger.log(Level.FINE, "Forum:couldn't register, username exists : " + aUsr.getDetails().getUsername() );
                     throw e;
               }
              catch(Exception e){
                     Forum.logger.log(Level.FINE, "Forum: problem registering : " + aUsr.getDetails().getUsername() + ": " + e.toString());
                     throw e;
              }
	}

        /**
         * The method changes the user permission of the given user to "Moderator"
         * @param curr_user , the user that initiated the change
         * @param to_change , the user that is to be come moderator
         */
        public void changeToModerator(User curr_user, User to_change){
            curr_user.changeToModerator(to_change);
            pipe.changeUserPermission(to_change.getDetails().getUsername(), "ModeratorPermission");
        }

   /**
    * Adds a permanent admin to our forum
    */
   public void addVitaly(){
       User vit=new User();
       String encryptedPass="";
                try {
                    encryptedPass = this.encryptPassword("1111");
                } catch (NoSuchAlgorithmException ex) {
                 Forum.logger.log(Level.FINE, "no such algorithm: "+ ex.toString());
                }
       Details d = new Details("sepetnit", encryptedPass, "@", "vitaly", "sepetnizki", "bash", "male");
       vit.setDetails(d);
       vit.setUp(PermissionFactory.getUserPermission("AdminPermission"));
       this.addToRegistered(vit);
        pipe.addRegUserToXml("sepetnit", encryptedPass, "@","vitaly","sepetnizki", "bash", "male","AdminPermission");

   }

   /**
    * Adds a permanent moderator to our forum
    */
   public void addYakir(){
         String encryptedPass="";
                try {
                    encryptedPass = this.encryptPassword("2222");
                } catch (NoSuchAlgorithmException ex) {
                 Forum.logger.log(Level.FINE, "no such algorithm: "+ ex.toString());
                }
       User yak=new User();
       Details d = new Details("dahany",encryptedPass, "@", "yakir", "dahan", "bash", "male");
       yak.setDetails(d);
       yak.setUp(PermissionFactory.getUserPermission("ModeratorPermission"));
         this.addToRegistered(yak);
       pipe.addRegUserToXml("dahany", encryptedPass, "@","yakir","dahan", "bash", "male","ModeratorPermission");
   }

   /**
    * creates the format of a message and its sons:
    *      7,8$$subj$$cont
    * @param message
    * @param fatherId
    * @return
    */
   private String createString(Message message,long fatherId){
       String toRet="";
       String subj,cont,user;
           if (message.getSubject().equals(""))
                    subj="EMPTY SUBJECT";
           else
               subj = message.getSubject();
           if (message.getContent().equals(""))
                    cont="EMPTY CONTENT";
           else
               cont = message.getContent();
       user = message.getCreator().getDetails().getUsername();
        toRet = fatherId+","+message.getMsg_id()+"$$"+subj+"$$"+cont+"$$"+user+"\n";
        if (message.getChild().size() ==0){
            return toRet;
        }

        for(Message m : message.getChild()){
            toRet+= createString(m, message.getMsg_id());
        }

       return toRet;
   }

    @Override
    public String toString(){
        String ans="";
        String subj="";
        String user="";
        String cont = "";
        ans = this._online_users.size() + "\n";
        System.out.println("online users: "+ans);
        int counter=0;
        if (this._online_users.size()==0)
            ans+="none,";
        else
            for(User useri : this._online_users.values()){
                ans +=useri.getDetails().getUsername()+ ",";
            }
        ans+="\n";

        for (Message msg : this._messages.values()){
            if (msg.getSubject().equals(""))
                    subj="EMPTY SUBJECT";
           else
               subj = msg.getSubject();
           if (msg.getContent().equals(""))
                    cont="EMPTY CONTENT";
           else
               cont = msg.getContent();
            user = msg.getCreator().getDetails().getUsername();
               ans +=  msg.getMsg_id()+"$$"+subj+"$$"+cont+"$$"+user+"\n";
                if (msg.getChild().size() !=0)
                for(Message m : msg.getChild()){
                    ans+= createString(m, msg.getMsg_id());
                }
        }
        return ans;
    }

    /**
     * under construction!
     * @param toSearch
     * @param u
     * @return
     */
        CompassHit[] searchByAuthor(String username, int from, int to) {
        CompassHit[] result = this._searchEng.searchByAuthor(username, from, to);
        //String[] stringMsgs = new String[result.length];
        System.out.println("***************************************");
        for(int i=0; i<result.length; i++){
            System.out.println(result[i]);
        }
        return result;
    }

    CompassHit[] searchByContent(String toSearch, int from, int to) {
        CompassHit[] result = this._searchEng.searchByContent(toSearch, from, to);
        return result;
    }
}//class
