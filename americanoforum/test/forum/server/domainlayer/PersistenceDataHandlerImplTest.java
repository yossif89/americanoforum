/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package forum.server.domainlayer;

import forum.server.persistencelayer.ForumType;
import java.io.FileOutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.TestCase;
import java.util.HashMap;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
/**
 *
 * @author visan
 */
public class PersistenceDataHandlerImplTest extends TestCase {

    PersistenceDataHandlerImpl pipe;

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
            FileOutputStream out = null;
        super.tearDown();
        ForumType forumtype = new ForumType();
        JAXBContext jc = JAXBContext.newInstance("forum.server.persistencelayer");
        Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // Write al; the data back to the XML file.
            out = new FileOutputStream("forum.xml");
	    m.marshal(forumtype,out);
            out.close();

    }


    /**
     * Test of addRegUserToXml method, of class PersistenceDataHandlerImpl.
     */
    public void testAddRegUserToXml() {
        try {
            pipe.addRegUserToXml("shassaf", Forum.encryptPassword("123"), "1@gmail.com", "assaf", "s", "b", "male", "LoggedInPermission");
            pipe.addRegUserToXml("sun",Forum.encryptPassword("123"), "1@gmail.com", "assaf", "s", "b", "male", "LoggedInPermission");
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
       
        
       Forum  forum = pipe.getForumFromXml();
       HashMap<String,User>  registers  = forum.getRegisteredUsers();
       User u1 = registers.get("shassaf");
    
       assertTrue(u1.getUp() instanceof  LoggedInPermission);
       Details d1 = u1.getDetails();
       assertTrue(d1.getUsername().equals("shassaf"));
        try {
            assertTrue(d1.getPassword().equals(Forum.encryptPassword("123")));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
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
        try {
            assertTrue(d3.getPassword().equals(Forum.encryptPassword("123")));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
       assertTrue(d3.getEmail().equals("1@gmail.com"));
       assertTrue(d3.getFirst_name().equals("assaf"));
       assertTrue(d3.getLast_name().equals("s"));
       assertTrue(d3.getAddress().equals("b"));
       assertTrue(d3.getGender().equals("male"));
         
    }

    /**
     * Test of addMsgToXml method, of class PersistenceDataHandlerImpl. */
     
    public void testAddMsgToXml() {
        try {
            pipe.addRegUserToXml("shassaf", Forum.encryptPassword("123"), "1@gmail.com", "assaf", "s", "b", "male", "LoggedInPermission");
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        pipe.addMsgToXml("test", "test1", 1, -1, "shassaf", new Date());
         pipe.addMsgToXml("test2", "test22", 2, 1, "shassaf", new Date());
        Forum  forum = pipe.getForumFromXml();
        HashMap<String,User>  registers  = forum.getRegisteredUsers();
       User u1 = registers.get("shassaf");
        HashMap<Long, Message> myMsg  = u1.getMyMessages();
        
         Message m2 = myMsg.get(new Long(2));
        Message m1 = myMsg.get(new Long(1));
        System.out.println(myMsg.toString());
     assertTrue(m2.getContent().equals("test22"));
       assertTrue(m2.getSubject().equals("test2"));
       assertTrue(m2.getCreator() == u1);
       
       assertTrue(m1.getContent().equals("test1"));
       assertTrue(m1.getSubject().equals("test"));
       assertTrue(m1.getCreator() == u1);

       

       Message forumsMessage = forum.getMessages().get(new Long(1));
       Message forumsMessage2 = forum.getMessages().get(new Long(2));
       assertTrue(forumsMessage==m1);
       assertTrue(forumsMessage2==null);
       assertTrue(forumsMessage.getChild().size()==1);
      assertTrue(forumsMessage.getChild().get(0) == m2);
    }

    /**
     * Test of modifyMsgInXml method, of class PersistenceDataHandlerImpl.
     */
    public void testModifyMsgInXml() {
        try {
            pipe.addRegUserToXml("shassaf", Forum.encryptPassword("123"), "1@gmail.com", "assaf", "s", "b", "male", "LoggedInPermission");
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
          pipe.addMsgToXml("test", "test1", 1, -1, "shassaf", new Date());
          pipe.addMsgToXml("test2", "test22", 2, 1, "shassaf", new Date());
          pipe.modifyMsgInXml(2, "modi1");
          pipe.modifyMsgInXml(2, "modi2");
          pipe.modifyMsgInXml(1, "modi3");
           Forum  forum = pipe.getForumFromXml();
          HashMap<String,User>  registers  = forum.getRegisteredUsers();
       User u1 = registers.get("shassaf");
        HashMap<Long, Message> myMsg  = u1.getMyMessages();
       Message m1 = myMsg.get(new Long(1));
         Message m2 = myMsg.get(new Long(2));

         assertTrue(m1.getContent().equals("modi3"));
         assertTrue(m2.getContent().equals("modi2"));
    }

}
