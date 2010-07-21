/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package forum.server.domainlayer;

import forum.server.persistencelayer.ForumType;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import junit.framework.TestCase;

/**
 *
 * @author Yossi
 */
public class ForumTest extends TestCase{
    private Forum _forum;
    private User _u1;
    private User _u2;
    private User _u3;
    private User _u4;
    public ForumTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
         this._forum=new Forum(false);
        Details d1 = new Details("shassaf", Forum.encryptPassword("123") , "1", "assaf", "sun", "b", "male");
        Details d2 = new Details("felberba", Forum.encryptPassword("1233"), "1", "yossi", "sun", "b", "male");
         Details d3 = new Details("iluxa13", Forum.encryptPassword("8888"), "4", "ilya", "g", "b", "male");
          Details d4 = new Details("visan", Forum.encryptPassword("7777"), "5", "hila", "v", "b", "female");
        _u1 = new User();
        _u2 = new User();
        _u3 = new User();
        _u4 = new User();
        _u1.setDetails(d1);
        _u2.setDetails(d2);
         _u3.setDetails(d3);
         _u4.setDetails(d4);
        this._forum.addToRegistered(_u1);
        this._forum.addToRegistered(_u2);
        this._forum.addToRegistered(_u3);
       
        _u1.setUp(PermissionFactory.getUserPermission("LoggedInPermission"));
         _u2.setUp(PermissionFactory.getUserPermission("AdminPermission"));
          _u3.setUp(PermissionFactory.getUserPermission("ModeratorPermission"));
           _u4.setUp(PermissionFactory.getUserPermission("GuestPermission"));
    }

    @Override
    protected void tearDown() throws Exception {


   /*           super.tearDown();

        FileOutputStream out = null;
  
        ForumType forumtype = new ForumType();
        JAXBContext jc = JAXBContext.newInstance("forum.server.persistencelayer");
        Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // Write al; the data back to the XML file.
            out = new FileOutputStream("forum.xml");
	    m.marshal(forumtype,out);
            out.close();
*/
    //    this._forum.setMessages(new HashMap<Long, Message>());
    }


    /**
     * Test of logoff method, of class Forum.
     */
    public void testLogoff() {
        System.out.println("logoff");
        try{
        this._forum.login("shassaf", "123");
        this._forum.login("felberba", "1233");
        }
        catch(Exception e){
            assertTrue(false);
        }
        this._forum.logoff(_u1);
        HashMap<String,User> online_users = this._forum.getOnlineUsers();
        HashMap<String,User> registered_users = this._forum.getRegisteredUsers();
        assertFalse(online_users.containsKey(_u1.getDetails().getUsername()));
        assertTrue(online_users.containsKey(_u2.getDetails().getUsername()));
        assertTrue(registered_users.containsKey(_u1.getDetails().getUsername()));
        assertTrue(registered_users.containsKey(_u2.getDetails().getUsername()));
        assertTrue(online_users.size()==1);
        //assertTrue(_u1.getUp() instanceof GuestPermission);
        assertTrue(_u2.getUp() instanceof AdminPermission);
    }

     /**
     * Test of logoff method, of class Forum.
     */
    public void testAddMessage() {

        System.out.println("add message test: ");
        try{
              this._forum.addMessage("subj", "cont", _u4);
        }
        catch(Exception e){
               assertTrue(true);
        }
        try{
         this._forum.addMessage("subj1", "cont1", _u1);
         Message m1 = _forum.getMessages().get(Message.getGensym()-1);
        this._forum.addMessage("subj2", "cont2", _u2);
        this._forum.addMessage("subj3", "cont3", _u3);
        this._forum.addReply("test", "test", _u1, m1);
        }
        catch(Exception e){
                 assertTrue(false);
        }
        assertEquals(this._forum.getMessages().size(),3);
        assertEquals(_u1.getMyMessages().size(),2);
         assertEquals(_u2.getMyMessages().size(),1);
          assertEquals(_u3.getMyMessages().size(),1);
           assertEquals(_u4.getMyMessages().size(),0);
    }

      /**
     * Test of register method, of class Forum.
     */
    public void testDelete() {
           System.out.println("delete test: ");
       Long n1=(long)0,n2=(long)0,n3=(long)0;
        try{
            n1=Message.getGensym();

        this._forum.addMessage("subj", "cont", _u1);
            n2=Message.getGensym();
        this._forum.addMessage("odtaot", "shonot", _u3);
            n3=Message.getGensym();
        this._forum.addMessage("odaa", "shon", _u1);

        }
        catch(Exception e){
            assertTrue(false);
        }
        assertEquals(this._forum.getMessages().size(), 3);
        HashMap<Long,Message>  forumMess =  this._forum.getMessages();
        Message m1 = forumMess.get(n1);
        Message m2 = forumMess.get(n2);
        Message m3 = forumMess.get(n3);
        this._u1.reply(m3, "replay", "test");

        try{
        this._forum.deleteMessage(m2, _u1);
        assertTrue(false);
        }
        catch(Exception e){
            assertTrue(true);
        }
        try{
            this._forum.deleteMessage(m1, _u3);
            this._forum.deleteMessage(m2, _u3);
            this._forum.deleteMessage(m3, _u3);
        }
        catch(Exception e){

            assertTrue(false);
        }
        assertEquals(forumMess.size(), 0);
        assertEquals(this._u1.getMyMessages().size(), 0);
        assertEquals(this._u3.getMyMessages().size(), 0);
    }

     /**
     * Test of login method, of class Forum.
     */
    public void testLogin() {
        System.out.println("login");
        try{
        this._forum.login("shassaf", "123");
        }
        catch(Exception e){
            assertTrue(false);
        }
        HashMap<String,User> online_users = this._forum.getOnlineUsers();
        HashMap<String,User> registered_users = this._forum.getRegisteredUsers();
        assertTrue(online_users.containsKey(_u1.getDetails().getUsername()));
        assertFalse(online_users.containsKey(_u2.getDetails().getUsername()));
        assertTrue(registered_users.containsKey(_u1.getDetails().getUsername()));
        assertTrue(registered_users.containsKey(_u2.getDetails().getUsername()));
        assertTrue(online_users.size()==1);
        assertTrue(_u1.getUp() instanceof LoggedInPermission);
        assertTrue(_u4.getUp() instanceof GuestPermission);
    }


    public void testChangePermission() {
        Long n1 = Message.getGensym();
        try{
          this._forum.addMessage("shemesh", "cont", _u1);
        }
        catch(Exception e){
            assertTrue(false);
        }
        try{
        this._u3.changeToModerator(_u4);
        }
        catch(Exception e){
            assertTrue(true);
        }

        try{
            this._u2.changeToModerator(_u4);
        }
        catch(Exception e){
            assertTrue(false);
        }

        try{
            this._u4.deleteMessage(_forum.getMessages().get(n1));
        }
        catch(Exception e){
            assertTrue(false);
        }
    }

    

  

    
}
