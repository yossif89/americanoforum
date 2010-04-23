package forum.client.controllerlayer;

import java.awt.Component;

import forum.client.ui.events.*;
import forum.tcpcommunicationlayer.*;
/**
 * You need to delete all the code in here and implement it yourself.<br>
 * This code is just for you to understand how to work with the Observer/Observed and GUI.
 * 
 * @author Tomer Heber
 */
public class ControllerHandlerImpl extends ControllerHandler {

   private  ClientConnectionController _connectionController;

        public ControllerHandlerImpl(){
            try{
                _connectionController = new ClientConnectionController("127.0.0.1", (short)1234);
            }
            catch(Exception e){
                _connectionController = null;
                ClientConnectionController.log.severe("Client: Couldn't connect to the server!");
            }
        }
        
	/* (non-Javadoc)
	 * @see forumtree.contol.ControllerHandler#getForumView()
	 */
	@Override
	public String getForumView( ) {
           String ans=  this._connectionController.communicate("view_forum",null);
           System.out.println("Forum view : \n "+ans);
           return ans;
	}

	@Override
	public void modifyMessage(long id, String newContent, Component comp) {
                Object[] args = new Object[2];
                args[0]= id;
                args[1] = newContent;
	        if ((this._connectionController.communicate("modify_message",args)).equals("ok"))
                        	notifyObservers(new ForumTreeRefreshEvent(comp,getForumView()));
                else{
                    ClientConnectionController.log.severe("Client: couldn't modify message");
                }
	}

	@Override
	public void addReplyToMessage(long id, String string, Component comp) {
		Object[] args = new Object[2];
                args[0]= id;
                args[1] = string;
	        if ((this._connectionController.communicate("add_reply",args)).equals("ok"))
                        	notifyObservers(new ForumTreeRefreshEvent(comp,getForumView()));
                else{
                    ClientConnectionController.log.severe("Client: couldn't reply to the  message");
                }
	}

	@Override
	public void deleteMessage(long id, Component comp) {
		Object[] args = new Object[1];
                args[0]= id;
	        if ((this._connectionController.communicate("delete_message",args)).equals("ok"))
                        	notifyObservers(new ForumTreeRefreshEvent(comp,getForumView()));
                else{
                    ClientConnectionController.log.severe("Client: couldn't delete to the  message");
                }
	}

	@Override
	public void addNewMessage(String subj , String cont ,Component comp) {
		Object[] args = new Object[2];
                args[0]= subj  ;
                args[1] = cont;
	        if ((this._connectionController.communicate("add_message",args)).equals("ok"))
                        	notifyObservers(new ForumTreeRefreshEvent(comp,getForumView()));
                else{
                    ClientConnectionController.log.severe("Client: couldn't add to the  message");
                }
		
	}

}
