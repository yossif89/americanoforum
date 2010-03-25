package forum.server.domainlayer;

/**
 *The interface of a general user permission , with all its needed method
 * */
public interface UserPermission {

        /**
         * Adds a message with the subject subj and the content cont to the forum.
         * On success does nothing , on failure throws an exception
         * @param aSbj
         * @param aCont
         */
	public void addMessage(String aSbj, String aCont);

        /**
         * modifies a message with the subject subj and the content cont to the forum.
         * On success does nothing , on failure throws an exception
         * @param aUsr
         * @param aMsg
         * @param aCont
         */
	public void modifyMessage(User aUsr, Message aMsg, String aCont);

        /**
         * vies the message given.
         *  On success does nothing , on failure throws an exception
         * @param aMsg
         */
	public void viewMessage(Message aMsg);

        /**
          * replies to the aParent message.
         *  On success does nothing , on failure throws an exception
         * @param aParent_msg
         * @param aSbj
         * @param aCont
         */
	public void reply(Message aParent_msg, String aSbj, String aCont);

         /**
          * deletes the given  message.
         *  On success does nothing , on failure throws an exception
         * @param aParent_msg
         * @param aSbj
         * @param aCont
         */
        public void deleteMessage(Message msg);

         /**
          * changes the given user to moderator.
         *  On success does nothing , on failure throws an exception
         * @param aParent_msg
         * @param aSbj
         * @param aCont
         */
        public void changeToModerator(User tUsr);

        
}