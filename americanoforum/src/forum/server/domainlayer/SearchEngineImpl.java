/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package forum.server.domainlayer;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
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
        Collection<Message> msgs = allMsgs.values();
        for(Message msg : msgs){
            addData(msg);
            System.out.println("msg: "+msg);
        }
    }

   public Index getIndex(){
       return this._index;
   }

    public void addData(Message msg){
        System.out.println("adding msg to index: "+msg);
        String sbj = msg.getSubject();
        String cont = msg.getContent();
        StringTokenizer toki_sbj = new StringTokenizer(sbj,"[ \t\n\r\f.-]");
        StringTokenizer toki_cont = new StringTokenizer(cont,"[ \t\n\r\f.-]");
        while(toki_sbj.hasMoreTokens()){
            String tmp = toki_sbj.nextToken();
            Integer word_id = this._index.addWord(tmp);
            this._index.addRelation(word_id, new Integer(msg.getMsg_id()));
        }
        while(toki_cont.hasMoreTokens()){
            String tmp = toki_cont.nextToken();
            Integer word_id = this._index.addWord(tmp);
            this._index.addRelation(word_id, new Integer(msg.getMsg_id()));
        }
    }

    public SearchHit[] searchByAuthor(String username, int from, int to) {
        //SearchHit[] result = new SearchHit[to-from];
        System.out.println("searching for msgs");
        Vector<SearchHit> author_msgs = new Vector<SearchHit>();
        int index = 0;
        Collection<Message> all_msgs = this._index.getAllMsgs().values();
        System.out.println("searchengine "+((Object)this._index.getAllMsgs()).toString());
        for(Message msg : all_msgs){
            System.out.println("msg = "+msg);
            if (msg.getCreator().getDetails().getUsername().equals(username)){
                author_msgs.add(new SearchHit(msg, 0, msg.getMsg_id()));
            }
        }
        Collections.sort(author_msgs, new SearchHitComparator());
        int size;
        if ((to-from) > author_msgs.size())
            size = author_msgs.size();
        else
            size = to-from;
        SearchHit[] result = new SearchHit[size];
        for(int i=0; i<size; i++){
            result[i] = author_msgs.get(from + i);
        }
        return result;
    }

    public SearchHit[] searchByContent(String phrase, int from, int to) {
        Vector<SearchHit> relevant_msgs = new Vector<SearchHit>();
        StringTokenizer toki_phrase = new StringTokenizer(phrase,"[ \t\n\r\f.-]");
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
        int size;
        if ((to-from) > relevant_msgs.size())
            size = relevant_msgs.size();
        else
            size = to-from;
        SearchHit[] result = new SearchHit[size];
        Collections.sort(relevant_msgs, new SearchHitComparator());
        //Exception!! if the number of requested results is not available!!!!
        for(int i=0; i<(to-from); i++){
            result[i] = relevant_msgs.get(from + i);
        }
        return result;
    }

    public void removeMessage(Message msg) {
        StringTokenizer toki_sbj = new StringTokenizer(msg.getSubject(),"[ \t\n\r\f.-]");
        StringTokenizer toki_cont = new StringTokenizer(msg.getContent(),"[ \t\n\r\f.-]");
        while(toki_cont.hasMoreTokens()){
            String word = toki_cont.nextToken();
            Integer word_id = this._index.getWordID(word);
            this._index.removeWord(word,word_id,msg.getMsg_id());
        }
        while(toki_sbj.hasMoreTokens()){
            String word = toki_sbj.nextToken();
            Integer word_id = this._index.getWordID(word);
            this._index.removeWord(word,word_id,msg.getMsg_id());
        }
    }

    public void setAllMessages(HashMap<Integer, Message> _allMessages) {
        this._index.setAllMegs(_allMessages);
    }

    

    
}
