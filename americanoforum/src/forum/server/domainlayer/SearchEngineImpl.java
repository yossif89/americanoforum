/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package forum.server.domainlayer;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 *
 * @author Yossi
 */
public class SearchEngineImpl implements SearchEngine{

    private Index _index;

    public SearchEngineImpl(HashMap<Integer, Message> allMsgs){
        this._index = new Index(allMsgs);
    }

    public void addData(Message msg){
        String sbj = msg.getSubject();
        String cont = msg.getContent();
        StringTokenizer toki_sbj = new StringTokenizer(sbj,"[\t\n\r\f.-]");
        StringTokenizer toki_cont = new StringTokenizer(cont,"[\t\n\r\f.-]");
        while(toki_sbj.hasMoreTokens()){
            Integer word_id = this._index.addWord(toki_sbj.nextToken());
            this._index.addRelation(word_id, new Integer(msg.getMsg_id()));
        }
        while(toki_cont.hasMoreTokens()){
            Integer word_id = this._index.addWord(toki_cont.nextToken());
            this._index.addRelation(word_id, new Integer(msg.getMsg_id()));
        }
    }

    public SearchHit[] searchByAuthor(String username, int from, int to) {
        SearchHit[] result = new SearchHit[to-from];
        Vector<SearchHit> author_msgs = new Vector<SearchHit>();
        int index = 0;
        Collection<Message> all_msgs = this._index.getAllMsgs().values();
        for(Message msg : all_msgs){
            if (msg.getCreator().getDetails().getUsername().equals(username)){
                author_msgs.add(new SearchHit(msg, 0, msg.getMsg_id()));
            }
        }
        Collections.sort(author_msgs, new SearchHitComparator());
        for(int i=0; i<(to-from); i++){
            result[i] = author_msgs.get(to + i);
        }
        return result;
    }

    public SearchHit[] searchByContent(String phrase, int from, int to) {
        SearchHit[] result = new SearchHit[to-from];
        Vector<SearchHit> relevant_msgs = new Vector<SearchHit>();
        StringTokenizer toki_phrase = new StringTokenizer(phrase,"[\t\n\r\f.-]");
        while(toki_phrase.hasMoreTokens()){
            String word = toki_phrase.nextToken();
            Integer word_id = this._index.getWordID(word);
            Vector<Message> word_msgs = this._index.getMsgsByWordID(word_id);
            int flag=0;
            for (Message msg : word_msgs){
                for(SearchHit hit : relevant_msgs){
                    if (msg.equals(hit.getMessage())){
                       hit.incScore();
                       flag=1;
                    }
                }
                if (flag==0){
                    SearchHit new_hit = new SearchHit(msg, 1);
                    relevant_msgs.add(new_hit);
                }
                flag=0;
            }
        }

        //Exception!! if the number of requested results is not available!!!!!
        for(int i=0; i<(to-from); i++){
            result[i] = relevant_msgs.get(to + i);
        }
        return result;
    }

    

    
}
