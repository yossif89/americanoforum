/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package forum.server.domainlayer;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author Yossi
 */
public class Index {

    private static Long curr_word_id =(long)0;

    private HashMap<String,Long> words;
    private HashMap<Long, Message> _allMessages;
    private Hashtable<Long, Vector<Long>> relations;

    public Index(HashMap<Long, Message> allMsgs){
        this.words = new HashMap<String,Long>();
        this._allMessages = allMsgs;
        System.out.println("index ktovet "+allMsgs);
        this.relations = new Hashtable<Long, Vector<Long>>();
    }

    public void setAllMegs(HashMap<Long, Message>  allMsgs){
        this._allMessages = allMsgs;
    }

    public Long addWord(String word){
        if (!words.containsKey(word)){
            words.put(word, curr_word_id);
            long tmp = curr_word_id.longValue();
            curr_word_id++;
            return new Long(tmp);
        }
        else
            return words.get(word);
    }

    public void addRelation(Long word_id, Long message_id){
        if (!this.relations.containsKey(word_id)){
            Vector<Long> msg_ids = new Vector<Long>();
            msg_ids.add(message_id);
            this.relations.put(word_id, msg_ids);
        }
        else{
            if(!this.relations.get(word_id).contains(message_id)){
                this.relations.get(word_id).add(message_id);
            }
        }
    }

    public Vector<Long> getMsgsIDsForWord(Long word_id){
        return this.relations.get(word_id);
    }

    public Vector<Message> getMsgsByWordID(Long word_id){
        Vector<Long> msgs_ids = this.getMsgsIDsForWord(word_id);
        Vector<Message> result = new Vector<Message>();
        for(Long id : msgs_ids){
            result.add(this._allMessages.get(id));
        }
        return result;
    }

    public HashMap<Long, Message> getAllMsgs(){
        return this._allMessages;
    }

    public Long getWordID(String word) {
        return this.words.get(word);
    }

    public void removeWord(String word, Long word_id, Long msg_id) {
        if ((word_id==null) || (!this.relations.containsKey(word_id)))
            return;
        Vector<Long> msg_vect = this.relations.get(word_id);
        if (msg_vect.size() == 1){
            this.relations.remove(word_id);
            this.words.remove(word);
        }
        else{
            msg_vect.remove(msg_id);
        }
    }

    

}
