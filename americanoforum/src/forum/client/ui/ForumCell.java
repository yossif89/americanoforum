package forum.client.ui;

import java.util.Vector;

/**
 * A forum cell is the an object that is attached to each node in the tree (JTree).
 * 
 * @author Tomer Heber
 */
public class ForumCell {
	
	/**
	 * A unique identifier of a message. 
	 */
	private long m_id;
	
	private String m_userName;
	private String m_content;
        private String m_subject;
	
	/**
	 * The sons of this ForumCell.
	 */
	private Vector<ForumCell> m_sons;
	
	public ForumCell(long id, String userName,String subject, String content) {
		m_id = id;
		m_userName = userName;
		m_content = content;
		m_subject = subject;
		m_sons = new Vector<ForumCell>();
	}

	public Vector<ForumCell> getSons() {
		return m_sons;
	}
	
	@Override
	public String toString() {
		return m_userName+"  --  "+m_content.substring(0,Math.min(45,m_content.length()));
	}

    public String getM_content() {
        return m_content;
    }

    public void setM_content(String m_content) {
        this.m_content = m_content;
    }

    public String getM_subject() {
        return m_subject;
    }

    public void setM_subject(String m_subject) {
        this.m_subject = m_subject;
    }

    public String getM_userName() {
        return m_userName;
    }

    public void setM_userName(String m_userName) {
        this.m_userName = m_userName;
    }

	/**
	 * Add a ForumCell cell to the the sons vector of this cell.
	 * 
	 * @param cell The cell to be added as a son to this cell.
	 */
	public void add(ForumCell cell) {
		m_sons.add(cell);
	}

	/**
	 * 
	 * @return The id of this ForumCell(Message).
	 */
	public long getId() {
		return m_id;
	}

}
