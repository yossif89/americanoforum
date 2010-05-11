/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package forum.server.domainlayer;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import org.compass.core.Compass;
import org.compass.core.CompassHit;
import org.compass.core.CompassHits;
import org.compass.core.CompassSession;
import org.compass.core.config.CompassConfiguration;
import org.compass.core.config.CompassConfigurationFactory;

/**
 *
 * @author Yossi
 */
public class SearchEngineImpl implements SearchEngine{


    static File file = new File("compassSettings.xml");
    static CompassConfiguration conf = CompassConfigurationFactory.newConfiguration().configure(file);
    private Compass compass = conf.buildCompass();
    private CompassSession session;

    /**
     * constructor
     * @param allMsgs all the messages in the system
     */
    public SearchEngineImpl(HashMap<Long, Message> allMsgs){
       conf.setSetting("org.apache.lucene.store.jdbc.dialect.MySQLDialect","MyDriver");
        openSession();
        Collection<Message> msgs = allMsgs.values();
        for(Message msg : msgs){
            addData(msg);
        }
    }

   /**
	 * Adds the Message msg to the search engine.
	 * Fields that should be indexed:<br>
	 *  1. Each word in the content of the message.<br>
	 *  2. The username of the author who wrote the message.<br>
	 *
	 * @param msg The message which we want to add to the indexing data base of the search engine.
	 */
    public void addData(Message msg){
        openSession();
       session.save(msg);
       Forum.logger.info("Added the message with subject "+ msg.getSubject());
    }

    /**
	 * Search for all the messages(SearchHit) written by the author called username.<br><br>
	 *
	 * Example: <i>searchByAuthor("Jonny82",0,10)</i> will return the first ten hits(messages) written
	 * by the author/user Jonny82.
	 *
	 * @param username The username of the author who wrote the message.
	 * @param from hits from index from
	 * @param to hits until index to
	 *
	 * @return The search hits from index from till index to - 1
	 */
    public CompassHit[] searchByAuthor(String username, int from, int to) {
        openSession();
       CompassHits hits = session.find("creator:\""+username+"\"");
       Forum.logger.info("aaaaaaaaaaaaaaaaaaai_"+ ((Message)hits.data(0)).getUsername());
       Forum.logger.info("username to search _"+"creator:\""+username+"\"");
       Forum.logger.info("hitsize= "+hits.length());
       return hits.detach(from,to).getHits();
//       CompassHit[] toRet = new CompassHit[hits.length()];
//       for(int i =0;i<hits.length();i++)
//        toRet[i] = hits.detach().getHits()
//       return toRet;
    }

    /**
	 * Search for all the messages(SearchHit) which contain the phrase.<br><br>
	 *
	 * Examples:
	 * // Search for the exact phrase read my lips and return hits 10-19.
	 * searchByContent(\""read my lips"\",10,20);
	 *
	 * // Search for messages who contain either apples, bananas <b>or</b> both and return hits 5-10.
	 * searchByContent("apples OR bananas",5,10);
	 *
	 * //Search for messages who must contain both oranges <b>and</b> apples and return hits 0-9.
	 * searchByContent("oranges AND apples",0,10);
	 *
	 * @param phrase The phrase which we would like to search
	 * @param from hits from index from
	 * @param to hits until index to
	 *
	 * @return The search hits from index from till index to - 1
	 */
    public CompassHit[] searchByContent(String phrase, int from, int to) {
      openSession();
        CompassHits hits = session.find("allCont:\""+phrase+"\"");
        return hits.detach(from, to).getHits();
    }

    /**
         * removing a message fro DB
         * @param msg the message to remove
         */
    public void removeMessage(Message msg) {
      session.delete(msg);
    }

    private void openSession(){
        if(this.session == null)
            this.session = compass.openSession();
    }

}
