/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package forum.server.persistencelayer;

import java.util.Date;
import junit.framework.TestCase;
import java.util.HashMap;
import forum.server.domainlayer.*;
/**
 *
 * @author visan
 */
public class PersistenceDataHandlerImplTest extends TestCase {

    public PersistenceDataHandlerImpl pipe;

    public PersistenceDataHandlerImplTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        pipe = new PersistenceDataHandlerImpl();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }


    /**
     * Test of addRegUserToXml method, of class PersistenceDataHandlerImpl.
     */
    public void testAddRegUserToXml() {
       pipe.addRegUserToXml("shassaf","123", "1@gmail.com", "assaf", "s", "b", "male", "LoggedInPermission");
        pipe.addRegUserToXml("sun","123", "1@gmail.com", "assaf", "s", "b", "male", "LoggedInPermission");
       Forum  forum = pipe.getForumFromXml();
       HashMap<String,User>  registers  = forum.getRegisteredUsers();
       User u1 = registers.get("shassaf");
       assertTrue(u1.getUp() instanceof  LoggedInPermission);
       Details d1 = u1.getDetails();
       assertTrue(d1.getUsername().equals("shassaf"));
       assertTrue(d1.getPassword().equals("123"));
       assertTrue(d1.getEmail().equals("1@gmail.com"));
       assertTrue(d1.getFirst_name().equals("assaf"));
       assertTrue(d1.getLast_name().equals("s"));
       assertTrue(d1.getAddress().equals("b"));
       assertTrue(d1.getGender().equals("male"));

        User u2 = registers.get("yossi");
        assertTrue(u2 == null);
        User u3 = registers.get("sun");
        assertTrue(u3.getUp() instanceof  LoggedInPermission);
       Details d3 = u3.getDetails();
       assertTrue(d3.getUsername().equals("sun"));
       assertTrue(d3.getPassword().equals("123"));
       assertTrue(d3.getEmail().equals("1@gmail.com"));
       assertTrue(d3.getFirst_name().equals("assaf"));
       assertTrue(d3.getLast_name().equals("s"));
       assertTrue(d3.getAddress().equals("b"));
       assertTrue(d3.getGender().equals("male"));
    }

    /**
     * Test of addMsgToXml method, of class PersistenceDataHandlerImpl.
     */
    public void testAddMsgToXml() {
        pipe.addRegUserToXml("shassaf","123", "1@gmail.com", "assaf", "s", "b", "male", "LoggedInPermission");
        pipe.addMsgToXml("test", "test1", 1, -1, "shassaf", new Date());
         pipe.addMsgToXml("test2", "test22", 2, 1, "shassaf", new Date());
        Forum  forum = pipe.getForumFromXml();
        HashMap<String,User>  registers  = forum.getRegisteredUsers();
       User u1 = registers.get("shassaf");
        HashMap<Integer, Message> myMsg  = u1.getMyMessages();
        Message m1 = myMsg.get(new Integer(1));
         Message m2 = myMsg.get(new Integer(2));

       assertTrue(m1.getContent().equals("test1"));
       assertTrue(m1.getSubject().equals("test"));
       assertTrue(m1.getCreator() == u1);

        assertTrue(m2.getContent().equals("test22"));
       assertTrue(m2.getSubject().equals("test2"));
       assertTrue(m2.getCreator() == u1);

       Message forumsMessage = forum.getMessages().get(new Integer(1));
       Message forumsMessage2 = forum.getMessages().get(new Integer(2));
       assertTrue(forumsMessage==m1);
       assertTrue(forumsMessage2==null);
       assertTrue(forumsMessage.getChild().size()==1);
      assertTrue(forumsMessage.getChild().get(0) == m2);
    }

    /**
     * Test of modifyMsgInXml method, of class PersistenceDataHandlerImpl.
     */
    public void testModifyMsgInXml() {
          pipe.addRegUserToXml("shassaf","123", "1@gmail.com", "assaf", "s", "b", "male", "LoggedInPermission");
          pipe.addMsgToXml("test", "test1", 1, -1, "shassaf", new Date());
          pipe.addMsgToXml("test2", "test22", 2, 1, "shassaf", new Date());
          pipe.modifyMsgInXml(2, "modi1");
          pipe.modifyMsgInXml(2, "modi2");
          pipe.modifyMsgInXml(1, "modi3");
           Forum  forum = pipe.getForumFromXml();
          HashMap<String,User>  registers  = forum.getRegisteredUsers();
       User u1 = registers.get("shassaf");
        HashMap<Integer, Message> myMsg  = u1.getMyMessages();
       Message m1 = myMsg.get(new Integer(1));
         Message m2 = myMsg.get(new Integer(2));

         assertTrue(m1.getContent().equals("modi3"));
         assertTrue(m2.getContent().equals("modi2"));
    }

}
