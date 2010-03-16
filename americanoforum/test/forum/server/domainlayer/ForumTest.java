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
 * @author Yossi
 */
public class ForumTest extends TestCase{
    private Forum _forum;
    private User _u1;
    private User _u2;

    public ForumTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
         this._forum=new Forum();
        Details d1 = new Details("shassaf", Forum.encryptPassword("123") , "1", "assaf", "sun", "b", "male");
        Details d2 = new Details("felberba", Forum.encryptPassword("1233"), "1", "yossi", "sun", "b", "male");
        _u1 = new User();
        _u2 = new User();
        _u1.setDetails(d1);
        _u2.setDetails(d2);
        this._forum.addToRegistered(_u1);
        this._forum.addToRegistered(_u2);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of login method, of class Forum.
     */
    public void testLogin() {
        System.out.println("login");
        this._forum.login("shassaf", "123");
        HashMap<String,User> online_users = this._forum.getOnlineUsers();
        HashMap<String,User> registered_users = this._forum.getRegisteredUsers();
        assertTrue(online_users.containsKey(_u1.getDetails().getUsername()));
        assertFalse(online_users.containsKey(_u2.getDetails().getUsername()));
        assertTrue(registered_users.containsKey(_u1.getDetails().getUsername()));
        assertTrue(registered_users.containsKey(_u2.getDetails().getUsername()));
        assertTrue(online_users.size()==1);
        assertTrue(_u1.getUp() instanceof LoggedInPermission);
        assertTrue(_u2.getUp() instanceof GuestPermission);
    }

    /**
     * Test of logoff method, of class Forum.
     */
    public void testLogoff() {
        System.out.println("logoff");
        this._forum.login("shassaf", "123");
        this._forum.login("felberba", "1233");
        this._forum.logoff(_u1);
        HashMap<String,User> online_users = this._forum.getOnlineUsers();
        HashMap<String,User> registered_users = this._forum.getRegisteredUsers();
        assertFalse(online_users.containsKey(_u1.getDetails().getUsername()));
        assertTrue(online_users.containsKey(_u2.getDetails().getUsername()));
        assertTrue(registered_users.containsKey(_u1.getDetails().getUsername()));
        assertTrue(registered_users.containsKey(_u2.getDetails().getUsername()));
        assertTrue(online_users.size()==1);
        assertTrue(_u1.getUp() instanceof GuestPermission);
        assertTrue(_u2.getUp() instanceof LoggedInPermission);
    }

    /**
     * Test of register method, of class Forum.
     */
    public void testRegister() {
        System.out.println("register");
        User t = new User();
        this._forum.register(t,"boli", "111", "2@hotmail.com","david", "d", "b", "male");
        HashMap<String,User> online_users = this._forum.getOnlineUsers();
        HashMap<String,User> registered_users = this._forum.getRegisteredUsers();
        assertTrue(online_users.size()==1);
        assertTrue(registered_users.size()==3);
        assertTrue(online_users.get("boli").getUp() instanceof LoggedInPermission);
        Details d3 = online_users.get("boli").getDetails();
        assertTrue(d3.getAddress().equals("b"));
        assertTrue(d3.getEmail().equals("2@hotmail.com"));
        assertTrue(d3.getFirst_name().equals("david"));
        assertTrue(d3.getGender().equals("male"));
        assertTrue(d3.getLast_name().equals("d"));
        try{
            assertTrue(d3.getPassword().equals(Forum.encryptPassword("111")));
        } catch (Exception e){
            e.printStackTrace();
        }
        assertTrue(d3.getUsername().equals("boli"));
    }

 /*   public void testModifyMessage() {
        this._forum.login(_u1.getDetails().getUsername(), _u1.getDetails().getPassword());
        this._forum.addMessage("test", "test test test", _u1);
        Message tMsg = this._forum._messages.get(new Integer(0));
        this._forum.addReply("reply1", "reply1 reply", _u1, tMsg);
        Message tReply = _u1.getMyMessages().get(new Integer(1));
        this._forum.modifyMessage(tMsg, "bla bla bla", _u1);

    }*/
}
