
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package forum.server.domainlayer;

import java.util.HashMap;
import java.util.Vector;
import junit.framework.TestCase;

/**
 *
 * @author Assaf
 */
public class SearchEngineImplTest extends TestCase {

    private User _u1;
    private User _u2;

 private   HashMap<Integer, Message> allMsgs;
  private Index  ind;
 private  SearchEngineImpl searchEngine;

    public SearchEngineImplTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        allMsgs = new HashMap<Integer, Message>();
        searchEngine = new SearchEngineImpl(allMsgs);
        this.ind = this.searchEngine.getIndex();
        Details d1 = new Details("shassaf", Forum.encryptPassword("123"), "1", "assaf", "sun", "b", "male");
        Details d2 = new Details("felberba", Forum.encryptPassword("1233"), "1", "yossi", "sun", "b", "male");
        _u1 = new User();
        _u2 = new User();
        _u1.setDetails(d1);
        _u2.setDetails(d2);
        /*
        Message m1 = new Message("test","bla bla",_u1);
         Message m2 = new Message("test2","bla2 bla2",_u1);
         Message m3 = new Message("test3","bla3 bla3",_u2);
         Message m4 = new Message("test4","bla4 bla4",_u2);
         this.allMsgs.put(m1.getMsg_id(), m4);
          this.allMsgs.put(m2.getMsg_id(), m2);
           this.allMsgs.put(m3.getMsg_id(), m3);
            this.allMsgs.put(m4.getMsg_id(), m4);

         
         */
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of addData method, of class SearchEngineImpl.
     */
    public void testAddData() {
         Message m1 = new Message("test","bla bla david",_u1);
         Message.incId();
         Message m2 = new Message("test2","bla2 bla2 david",_u1);
         Message.incId();

         this.allMsgs.put(m1.getMsg_id(), m1);
         this.allMsgs.put(m2.getMsg_id(), m2);
        
         this.searchEngine.addData(m1);
         this.searchEngine.addData(m2);


         Integer w1 = this.ind.getWordID("test");

         Vector<Message>  msgs1 =this.ind.getMsgsByWordID(w1);
         assertEquals(1, msgs1.size());
         assertTrue(msgs1.contains(m1));

         Integer w2 = this.ind.getWordID("bla2");
         Vector<Message>  msgs2 =this.ind.getMsgsByWordID(w2);
         assertEquals(1, msgs2.size());
         assertTrue(msgs2.contains(m2));

         Integer w3 = this.ind.getWordID("david");
         Vector<Message>  msgs3 =this.ind.getMsgsByWordID(w3);
         assertEquals(2, msgs3.size());
         assertTrue(msgs3.contains(m2));
         assertTrue(msgs3.contains(m1));

          Integer w4 = this.ind.getWordID("nothing");
         assertEquals(null,w4);
       
    }

    /**
     * Test of searchByAuthor method, of class SearchEngineImpl.
     */
    public void testSearchByAuthor() throws InterruptedException {
         Message m1 = new Message("test","bla bla",_u1);
         Message.incId();
         Thread.sleep(2000);
         Message m2 = new Message("test2","bla2 bla2",_u1);
         Message.incId();

         this.allMsgs.put(m1.getMsg_id(), m1);
         this.allMsgs.put(m2.getMsg_id(), m2);

         this.searchEngine.addData(m1); 
         this.searchEngine.addData(m2);

         SearchHit[] msgs = this.searchEngine.searchByAuthor(_u1.getDetails().getUsername(), 0, 2);
         assertTrue(msgs.length == 2);
         assertTrue(msgs[0].getMessage().equals(m2));
         assertTrue(msgs[1].getMessage().equals(m1));
    }

    /**
     * Test of searchByContent method, of class SearchEngineImpl.
     */
    public void testSearchByContent() {
         Message m1 = new Message("test","bla bla david",_u1);
         Message.incId();
         Message m2 = new Message("test2","bla2 bla david tikva",_u1);
         Message.incId();

         this.allMsgs.put(m1.getMsg_id(), m1);
         this.allMsgs.put(m2.getMsg_id(), m2);

         this.searchEngine.addData(m1);
         this.searchEngine.addData(m2);

         SearchHit[] result = this.searchEngine.searchByContent("bla2", 0,1);
         assertTrue(result.length==1);
         assertTrue(result[0].getMessage().equals(m2));

         SearchHit[] result2 = this.searchEngine.searchByContent("bla david tikva", 0,2);
         assertTrue(result2.length==2);
         assertEquals(result2[0].getScore(),3.0);
         assertEquals(result2[1].getScore(),2.0);
         assertTrue(result2[0].getMessage().equals(m2));
         assertTrue(result2[1].getMessage().equals(m1));
    }

}
