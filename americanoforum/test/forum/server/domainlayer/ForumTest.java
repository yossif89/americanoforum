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
        Details d1 = new Details("shassaf", "123", "1", "assaf", "sun", "b", "male");
        Details d2 = new Details("felberba", "1233", "1", "yossi", "sun", "b", "male");
        _u1 = new User();
        _u2 = new User();
        this._forum=new Forum();
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
        assertTrue(online_users.size()==0);
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
        assertTrue(d3.getPassword().equals("111"));
        assertTrue(d3.getUsername().equals("boli"));
    }

}
