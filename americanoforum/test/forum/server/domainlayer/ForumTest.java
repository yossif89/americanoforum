/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package forum.server.domainlayer;

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
        Vector<User> online_users = this._forum.getOnlineUsers();
        Vector<User> registered_users = this._forum.getRegisteredUsers();
        assertTrue(online_users.contains(_u1));
        assertFalse(online_users.contains(_u2));
        assertTrue(registered_users.contains(_u1));
        assertTrue(registered_users.contains(_u2));
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
        Vector<User> online_users = this._forum.getOnlineUsers();
        Vector<User> registered_users = this._forum.getRegisteredUsers();
        assertFalse(online_users.contains(_u1));
        assertTrue(online_users.contains(_u2));
        assertTrue(registered_users.contains(_u1));
        assertTrue(registered_users.contains(_u2));
        assertTrue(online_users.size()==0);
        assertTrue(_u1.getUp() instanceof GuestPermission);
        assertTrue(_u2.getUp() instanceof LoggedInPermission);
    }

    /**
     * Test of register method, of class Forum.
     */
    public void testRegister() {
        System.out.println("register");
        this._forum.register("boli", "111", "2@hotmail.com","david", "d", "b", "male");
        Vector<User> online_users = this._forum.getOnlineUsers();
        Vector<User> registered_users = this._forum.getRegisteredUsers();
        assertTrue(online_users.size()==1);
        assertTrue(registered_users.size()==3);
        assertTrue(online_users.firstElement().getUp() instanceof LoggedInPermission);
        Details d3 = online_users.firstElement().getDetails();
        assertTrue(d3.getAddress().equals("b"));
        assertTrue(d3.getEmail().equals("2@hotmail.com"));
        assertTrue(d3.getFirst_name().equals("david"));
        assertTrue(d3.getGender().equals("male"));
        assertTrue(d3.getLast_name().equals("d"));
        assertTrue(d3.getPassword().equals("111"));
        assertTrue(d3.getUsername().equals("boli"));




        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
