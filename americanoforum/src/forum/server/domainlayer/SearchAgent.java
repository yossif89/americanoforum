/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package forum.server.domainlayer;

import java.util.StringTokenizer;
import java.util.Vector;

/**
 *
 * @author Yossi
 */
public class SearchAgent {

    private Index _index;

    public SearchAgent(){
        this._index = new Index();
    }

    public void scanMessage(Message msg){
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

    

    
}
