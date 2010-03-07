package forum.server.domainlayer;

import java.util.HashMap;

public class Forum {
	HashMap<Integer, Message> _messages = new HashMap<Integer,Message>();
	HashMap<String, User> _registered = new HashMap<String, User>();
	HashMap<String, User> _online_users = new HashMap<String, User>();



        public void addMessage (String aSbj,String aCont , User aUsr){
           Message tMsg =  aUsr.addMessage(aSbj,aCont);
           _messages.put(tMsg.getMsg_id(), tMsg);

        }

        public void addToRegistered (User aUser){
            this._registered.put(aUser.getDetails().getUsername(),aUser);
        }

         public void addToOnline (User aUser){
            this._online_users.put(aUser.getDetails().getUsername(),aUser);
        }

         public void turnOffline(User aUser){
             this._online_users.remove(aUser);
         }


        public HashMap<String, User> getOnlineUsers(){
            return this._online_users;
        }

        public HashMap<String, User> getRegisteredUsers(){
            return this._registered;
        }

	public User login(String aUsername, String aPass) {
		throw new UnsupportedOperationException();
	}

	public void logoff(User aUser) {
		throw new UnsupportedOperationException();
	}

	public void register(String aUsername, String aPass, String aEmail, String aFirstName, String aLastName, String aAddress, String aGender) {
		throw new UnsupportedOperationException();
	}
}