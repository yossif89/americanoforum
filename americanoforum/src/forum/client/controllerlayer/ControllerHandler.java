package forum.client.controllerlayer;

import java.awt.Component;
import java.util.Observable;

/**
 * This abstract class is in charge of the communication between the UI layer and the Controller layer.
 * 
 * @author Tomer Heber
 */
public abstract class ControllerHandler extends Observable {

	@Override
	public synchronized void notifyObservers(Object o) {
		setChanged();
		super.notifyObservers(o);
	}
	
	/**
	 * 
	 * @return and encoded view of the forum.<br>
	 * An example of such an encoding can be some sort of XML string.<br>
	 * For example:<br>
	 * <message> id: user: sdfsdf content: fsdfsd
	 * 		<message> 
	 *			...
	 *		</message>
	 *		<message>
	 *			...
  	 *		</message>
	 * </message>
	 */
	public abstract String getForumView( String username);

	/**
	 * Tries to modify a message.
	 * 
	 * @param id The id of the message to be modified.
	 * @param newContent The new content of the message.
	 */
	public abstract void modifyMessage(long id, String newContent, Component comp);

	/**
	 * Adds a reply message.
	 * 
	 * @param id The id of the message to which we reply.
	 * @param string The content of the new message.
	 */
	public abstract void addReplyToMessage(long id, String subject,String content, Component comp);

        public abstract void logoff(Component comp) ;

        public abstract void login(String user,String pass, Component comp) ;

        public abstract  void register( String username, String password, String first, String last, String email, String address, String gender, Component  comp) ;
	/**
	 * Deletes recursively the message id and all his sons.
	 * 
	 * @param id The id of the message to delete.
	 */
	public abstract void deleteMessage(long id, Component comp);

	/**
	 * Adds a new message to the forum.
	 */
	public abstract void addNewMessage(String subj,String cont , Component comp);

        public abstract void searchByAuthor(String userToSearch,int from,int to, Component comp);

        public abstract void searchByContent(String phraseToSearch,int from,int to,Component comp);

        public abstract void showUsersToPromote(Component c);

        public abstract void promoteUserToModerator(String toPromote,Component comp);

}
