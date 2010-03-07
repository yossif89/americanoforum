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
           if (tUsr==null)
               throw new IllegalAccessError();
           if ( !tUsr.getDetails().getPassword().equals(aPass))
               throw new IllegalAccessError();
           this._online_users.put(aUsername, tUsr);
           tUsr.setUp(LoggedInPermission.getInstance());
          return tUsr;
	}

        /**
         * assumes that the user is in the logged in db , and removes it from that list
         * @param aUser
         */
	public void logoff(User aUser) {
		this._online_users.remove(aUser.getDetails().getUsername());
                aUser.setUp(GuestPermission.getInstance());
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
	public void register(User aUsr,String aUsername, String aPass, String aEmail, String aFirstName, String aLastName, String aAddress, String aGender) {
		Details d = new Details(aUsername, aPass, aEmail, aFirstName, aLastName, aAddress, aGender);
                aUsr.setDetails(d);
                aUsr.setUp(LoggedInPermission.getInstance());
                this._online_users.put(aUsername, aUsr);
                this._registered.put(aUsername, aUsr);
	}
}