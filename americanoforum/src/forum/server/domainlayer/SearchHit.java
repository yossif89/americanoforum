package forum.server.domainlayer;

/**
 * A single search hit returned after a search operation.
 * 
 * @author Tomer Heber
 */
public class SearchHit {
	
	private Message m_message;
	private double m_score;
        private int msg_id;

	public SearchHit(Message message, double score) {
		m_message = message;
		m_score = score;
	}

        public SearchHit(int msgID, double score) {
		msg_id = msgID;
		m_score = score;
                m_message = null;
	}

        public SearchHit(Message message, double score, int msgID) {
		msg_id = msgID;
		m_score = score;
                m_message = message;
	}

        public void setMessage(Message msg){
            m_message=msg;
        }
	
	public double getScore() {
		return m_score;
	}
	
	public Message getMessage() {
		return m_message;
	}

        public void incScore() {
            this.m_score++;
        }
        

}