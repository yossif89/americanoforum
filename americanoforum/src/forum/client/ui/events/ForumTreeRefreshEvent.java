package forum.client.ui.events;

import java.awt.Component;
import forum.client.ui.ForumTreeHandler;
import javax.swing.JOptionPane;

/**
 * This event enables a component (if there is one). And updates the forum view in the GUI.
 * 
 * @author Tomer Heber
 */
public class ForumTreeRefreshEvent implements ForumTreeEvent {

	private Component m_comp;
	private String m_forumView;
	
	
	public ForumTreeRefreshEvent(String forumView) {
		m_forumView = forumView;
		m_comp = null;
	}
	
	public ForumTreeRefreshEvent(Component comp, String forumView) {
		m_forumView = forumView;
		m_comp = comp;
	}
	
	/* (non-Javadoc)
	 * @see forumtree.contol.ForumTreeEvent#respondToEvent(forumtree.ForumTree)
	 */
	@Override
	public void respondToEvent(ForumTreeHandler handler) {
                if ((this.m_forumView.charAt(0)=='$')&&(this.m_forumView.charAt(1)=='$')){
                     JOptionPane.showMessageDialog(null,"The operation couldn't complete \n"+ this.m_forumView.substring(2), "Error",JOptionPane.ERROR_MESSAGE);
                }
                else{
                      JOptionPane.showMessageDialog(null,"The operation ended successfully");
                         handler.refreshForum(m_forumView);
                }
		if (m_comp != null) {
			m_comp.setEnabled(true);  
		}
		
		

	}

}
