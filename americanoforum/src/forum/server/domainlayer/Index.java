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
    //private HashMap<Integer,Message> messages; no need - in the forum.
    private Hashtable<Integer, Vector<Integer>> relations;

    public Index(){
        this.words = new HashMap<String,Integer>();
        //this.messages = new HashMap<Integer,Message>(); // needs to add messages in every getForum!!!!!!!!
        this.relations = new Hashtable<Integer, Vector<Integer>>();
    }

    public Integer addWord(String word){
        if (!words.containsKey(word)){
            words.put(word, curr_word_id);
            curr_word_id++;
            return curr_word_id;
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
            this.relations.get(word_id).add(message_id);
        }
    }

    public Vector<Integer> getMsgsIDsForWord(Integer word_id){
        return this.relations.get(word_id);
    }

    

}
