package forum.client.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @author Tomer Heber
 *
 */
public class SearchSelectedForumTreeCellPanel extends JPanel {

	private static final long serialVersionUID = 3195512056748314498L;

	private ForumTree m_forumTree;

        private JTextField m_subj;
	private JTextArea m_area;

	//private JButton m_modifyButton;
	//private JButton m_replyButton;
	//private JButton m_deleteButton;
        private ForumCell _cellData;

	public SearchSelectedForumTreeCellPanel(ForumTree forumTree) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		m_forumTree = forumTree;

		m_subj = new JTextField();
                m_subj.setText("");
                m_subj.setEditable(false);
                m_subj.setVisible(true);
		m_area = new JTextArea();
		m_area.setText("");
                m_area.setEditable(false);
		/*m_area.addKeyListener(new KeyListener() {

//
			@Override
			public void keyTyped(KeyEvent e) {
				if (!m_modifyButton.isEnabled()) {
					m_modifyButton.setEnabled(true);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {}
		});*/

		JScrollPane scroll = new JScrollPane(m_area);

		scroll.setPreferredSize(new Dimension(350,90));
                add(m_subj);
		add(scroll);

		//JPanel buttonPanel = new JPanel();
		//buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

	/*	m_modifyButton = new JButton("Modify");
		m_modifyButton.setEnabled(true);
		m_modifyButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				m_forumTree.modifyMessage(m_area.getText(),m_modifyButton);
			}
		});*/

	/*	m_replyButton = new JButton("Reply");
		m_replyButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
                                 new ReplyForm(m_forumTree,m_replyButton).setVisible(true);
			}
		});*/

	/*	m_deleteButton = new JButton("Delete");
		m_deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				m_forumTree.deleteMessage(m_deleteButton);

			}
		});*/

	/*	buttonPanel.add(m_modifyButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(5,0)));
		buttonPanel.add(m_replyButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(5,0)));
		buttonPanel.add(m_deleteButton);

		this.add(buttonPanel);*/
		this.add(Box.createRigidArea(new Dimension(0,2)));

		this.setPreferredSize(new Dimension(400,130));
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setBackground(Color.WHITE);
	}

	public void updatePanel(ForumCell cell) {
                this._cellData = cell;
		m_area.setText(cell.getM_content());
                m_subj.setText(cell.getM_userName()+"  |  "+cell.getM_subject());
		//m_modifyButton.setEnabled(false);
	}

}
