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
public class SearchResultEvent implements ForumTreeEvent{
        private Component m_comp;
	private String m_results;

	public SearchResultEvent(String forumView) {
		m_results = forumView;
		m_comp = null;
	}
	
	public SearchResultEvent(Component comp, String results) {
		m_results = results;
		m_comp = comp;
	}

        public void respondToEvent(ForumTreeHandler handler) {
            if (m_results.equals("")){
                JOptionPane.showMessageDialog(null, "No results were found!", "Results", JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                handler.showSearchResults(m_results);
            }
            if (m_comp != null) {
		m_comp.setEnabled(true);
            }
        }

}
