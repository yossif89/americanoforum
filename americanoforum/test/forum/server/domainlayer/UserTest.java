/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package forum.server.domainlayer;

import java.util.HashMap;
import junit.framework.TestCase;

/**
 *
 * @author Yossi
 */
public class UserTest extends TestCase {

    private Forum _forum;
    private User _u1;
    private User _u2;

    public UserTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Details d1 = new Details("shassaf", Forum.encryptPassword("123"), "1", "assaf", "sun", "b", "male");
        Details d2 = new Details("felberba", Forum.encryptPassword("1233"), "1", "yossi", "sun", "b", "male");
        _u1 = new User();
        _u2 = new User();
        _u1.setDetails(d1);
        _u2.setDetails(d2);
        this._forum=new Forum();
        this._forum.addToRegistered(_u1);
        this._forum.addToRegistered(_u2);
        this._forum.login("shassaf", "123");
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of addMessage method, of class User.
     */
    public void testAddMessage() {
        Message m = this._u1.addMessage("test", "test");
        assertTrue(m.getParent() == null);
        assertTrue(m.getCreator().equals(_u1));
        Message m2 = this._u1.getMyMessages().get(new Integer(m.getMsg_id()));
        assertEquals(m,m2);
        int flag=0;
        try{
            this._u2.addMessage("test2", "test2");
        }
        catch(Exception e){
            flag=1;
        }
        assertTrue(flag==1);
    }

    /**
     * Test of modifyMessage method, of class User.
     */
    public void testModifyMessage() {
        Message m = this._u1.addMessage("test", "test");
        this._u1.modifyMessage(m, "hello");
        Message m2 = this._u1.getMyMessages().get(new Integer(m.getMsg_id()));
        assertEquals(m2.getContent(),"hello");
        int flag=0;
        try{
            this._u2.modifyMessage(m, "test2");
        }
        catch(Exception e){
            flag=1;
        }
        assertTrue(flag==1);
        assertEquals(m2.getContent(),"hello");
    }

    /**
     * Test of reply method, of class User.
     */
    public void testReply() {
        Message m = this._u1.addMessage("test", "test");
        Message m2 = this._u1.reply(m, "test2", "test2");
        assertTrue(m2.getParent() == m);
        assertTrue(m.getCreator().equals(_u1));
        Message m3 = this._u1.getMyMessages().get(new Integer(m2.getMsg_id()));
        assertEquals(m3,m2);
    }

}
