package forum.server.domainlayer;

/**
 * A single search hit returned after a search operation.
 * 
 * @author Tomer Heber
 */
public class SearchHit {
	
	private Message m_message;
	private double m_score;
        private long msg_id;

        /**
         * constructor
         * @param message message of the hit
         * @param score score of hit
         */
	public SearchHit(Message message, double score) {
		m_message = message;
		m_score = score;
	}

        /**
         * constructor
         * @param msgID msg id of the hit
         * @param score score of the hit
         */
        public SearchHit(long msgID, double score) {
		msg_id = msgID;
		m_score = score;
                m_message = null;
	}

        /**
         * constructor
         * @param message the message of the hit
         * @param score the score of the hit
         * @param msgID the message id
         */
        public SearchHit(Message message, double score, long msgID) {
		msg_id = msgID;
		m_score = score;
                m_message = message;
	}

        /**
         * set the message field
         * @param msg the message to set
         */
        public void setMessage(Message msg){
            m_message=msg;
        }

        /**
         * returns the score of the hit
         * @return the score of the hit
         */
	public double getScore() {
		return m_score;
	}

        /**
         * returns the message of the hit
         * @return the message of the hit
         */
	public Message getMessage() {
		return m_message;
	}

        /**
         * incrementing the score by 1
         */
        public void incScore() {
            this.m_score++;
        }

        /**
         * returns a string with the information of the hit
         * @return
         */
    @Override
        public String toString(){
            return "msg_id: " + this.m_message.getMsg_id()+ "---> " +this.m_message.getSubject();
        }

        /**
         * returns true if the given object equals this hit
         * @param o the object to check
         * @return true if the object equals this hit, otherwise false
         */
        public boolean equals(Object o){
            if (!(o instanceof SearchHit)){
                return false;
            }
            SearchHit sh_o = (SearchHit)o;
            if((this.m_message == sh_o.m_message) && (this.msg_id == sh_o.msg_id))
                return true;
            return false;
        }

        /**
         * add the given score to this score
         * @param newScore the score to add
         */
        public void addToScore(double newScore){
            this.m_score = this.m_score + newScore;
        }


}
