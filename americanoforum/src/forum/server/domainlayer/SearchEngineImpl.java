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



    //basic searchByContent
    public SearchHit[] searchByContent(String phrase, int from, int to) {
        Vector<SearchHit> relevant_msgs = new Vector<SearchHit>();
        StringTokenizer toki_phrase = new StringTokenizer(phrase,"[ \t\n\r\f.-]");
        int numOfTokInPhrase = 0;
        if(phrase.contains(" OR ")){
            return searchByContentOR(phrase,from,to);
        }
        if(phrase.contains(" AND ")){
            return searchByContentAND(phrase,from,to);
        }
        while(toki_phrase.hasMoreTokens()){
            numOfTokInPhrase++;
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
        Vector<SearchHit> final_msgs = new Vector<SearchHit>();
        for(SearchHit hit : relevant_msgs){
            if(hit.getScore()==numOfTokInPhrase){ //ifthe score is max
                if ((hit.getMessage().getContent().contains(phrase))
                    || (hit.getMessage().getSubject().contains(phrase))){
                    final_msgs.add(hit);
                }
            }
        }
        int size;
        if ((to-from) > final_msgs.size())
            size = final_msgs.size();
        else
            size = to-from;
        SearchHit[] result = new SearchHit[size];
        Collections.sort(final_msgs, new SearchHitComparator());
        //Exception!! if the number of requested results is not available!!!!
        try{
            for(int i=0; i<size; i++){
                result[i] = final_msgs.get(from + i);
            }
        } catch(ArrayIndexOutOfBoundsException e){
            e.printStackTrace(); //to implement a user interface message
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

    private SearchHit[] searchByContentOR(String phrase, int from, int to) {
        int OR_index = phrase.indexOf("OR");
        String first_phrase = phrase.substring(0, OR_index);
        String second_phrase = phrase.substring(OR_index+3, phrase.length());
        SearchHit[] first_search = searchByContent(first_phrase,0,to);
        SearchHit[] second_search = searchByContent(second_phrase, 0, to);
        Vector<SearchHit> final_ans = new Vector<SearchHit>();
        for(int i=0; i<first_search.length; i++){
            final_ans.add(first_search[i]);
        }
        for(int i=0; i<second_search.length; i++){
            //final_ans.add(second_search[i]);
            boolean flag_found = false;
            for(int j=0; j<final_ans.size(); j++){
                if(final_ans.elementAt(j).getMessage().getMsg_id() == second_search[i].getMessage().getMsg_id()){
                    final_ans.elementAt(j).addToScore(second_search[i].getScore());
                    flag_found=true;
                }
            }
            if (flag_found == false){
                final_ans.add(second_search[i]);
            }
        }
        Collections.sort(final_ans, new SearchHitComparator());
        int size;
        if ((to-from) > final_ans.size())
            size = final_ans.size();
        else
            size = to-from;
        SearchHit[] result = new SearchHit[size];
        try{
            for(int i=0; i<size; i++){
                result[i] = final_ans.get(from + i);
            }
        } catch(ArrayIndexOutOfBoundsException e){
            e.printStackTrace(); //to implement a user interface message
        }
        return result;
    }

    private SearchHit[] searchByContentAND(String phrase, int from, int to) {
        int AND_index = phrase.indexOf("AND");
        String first_phrase = phrase.substring(0, AND_index);
        String second_phrase = phrase.substring(AND_index+4, phrase.length());
        SearchHit[] first_search = searchByContent(first_phrase,0,to);
        SearchHit[] second_search = searchByContent(second_phrase, 0, to);
        Vector<SearchHit> final_ans = new Vector<SearchHit>();
        for(int i=0; i<first_search.length; i++){
            for(int j=0; j<second_search.length; j++){
                if(first_search[i].getMessage().getMsg_id() == second_search[j].getMessage().getMsg_id()){
                    first_search[i].addToScore(second_search[j].getScore());
                    final_ans.add(first_search[i]);
                }
            }
        }
        Collections.sort(final_ans, new SearchHitComparator());
        int size;
        if ((to-from) > final_ans.size())
            size = final_ans.size();
        else
            size = to-from;
        SearchHit[] result = new SearchHit[size];
        try{
            for(int i=0; i<size; i++){
                result[i] = final_ans.get(from + i);
            }
        } catch(ArrayIndexOutOfBoundsException e){
            e.printStackTrace(); //to implement a user interface message
        }
        return result;
    }

    

    
}
