/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package forum.client.ui.events;

import forum.client.ui.ForumTreeHandler;
import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author Yossi
 */
public class ShowAllUsersEvent implements ForumTreeEvent{
        private Component m_comp;
	private String m_results;

	public ShowAllUsersEvent(String users) {
		m_results = users;
		m_comp = null;
	}

	public ShowAllUsersEvent(Component comp, String results) {
		m_results = results;
		m_comp = comp;
	}

        public void respondToEvent(ForumTreeHandler handler) {
            handler.showPromoteFrame(m_results);
            
            if (m_comp != null) {
		m_comp.setEnabled(true);
            }
        }

}
