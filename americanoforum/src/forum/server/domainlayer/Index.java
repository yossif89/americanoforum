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

    private static Integer curr_word_id =0;

    private HashMap<String,Integer> words;
    private HashMap<Integer, Message> _allMessages;
    private Hashtable<Integer, Vector<Integer>> relations;

    public Index(HashMap<Integer, Message> allMsgs){
        this.words = new HashMap<String,Integer>();
        this._allMessages = allMsgs; 
        this.relations = new Hashtable<Integer, Vector<Integer>>();
    }

    public void setAllMegs(HashMap<Integer, Message>  allMsgs){
        this._allMessages = allMsgs;
    }

    public Integer addWord(String word){
        if (!words.containsKey(word)){
            words.put(word, curr_word_id);
            int tmp = curr_word_id.intValue();
            curr_word_id++;
            return new Integer(tmp);
        }
        else
            return words.get(word);
    }

    public void addRelation(Integer word_id, Integer message_id){
        if (!this.relations.containsKey(word_id)){
            Vector<Integer> msg_ids = new Vector<Integer>();
            msg_ids.add(message_id);
            this.relations.put(word_id, msg_ids);
        }
        else{
            if(!this.relations.get(word_id).contains(message_id)){
                this.relations.get(word_id).add(message_id);
            }
        }
    }

    public Vector<Integer> getMsgsIDsForWord(Integer word_id){
        return this.relations.get(word_id);
    }

    public Vector<Message> getMsgsByWordID(Integer word_id){
        Vector<Integer> msgs_ids = this.getMsgsIDsForWord(word_id);
        Vector<Message> result = new Vector<Message>();
        for(Integer id : msgs_ids){
            result.add(this._allMessages.get(id));
        }
        return result;
    }

    public HashMap<Integer, Message> getAllMsgs(){
        return this._allMessages;
    }

    public Integer getWordID(String word) {
        return this.words.get(word);
    }

    

}
