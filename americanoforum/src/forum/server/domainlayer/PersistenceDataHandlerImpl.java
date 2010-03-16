/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package forum.server.domainlayer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import forum.server.persistencelayer.*;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author Yossi
 */
public class PersistenceDataHandlerImpl implements PersistenceDataHandler {

   // private forum.server.persistencelayer.Forum _forum; // the data forum

   // public PersistenceDataHandlerImpl(){
    //    this._forum=null;
   // }

    //creates all the messages and adds them to the users.
    private HashMap<Integer, Message> getMsgs(List<MessageType> msgs, HashMap<String,User> users){
        HashMap<Integer, Message> all_messages = new HashMap<Integer,Message>();
        for (MessageType msg : msgs){
            User creator = users.get(msg.getCreator());
            Message newMsg = new Message(msg.getSubject(), msg.getContent(), creator);
            newMsg.setMsg_id(msg.getMessageId());
            GregorianCalendar c = msg.getDate().toGregorianCalendar();
            newMsg.setDate(c.getTime());
            all_messages.put(new Integer(msg.getMessageId()), newMsg);
            creator.getMyMessages().put(new Integer(newMsg.getMsg_id()), newMsg);

        }
        HashMap<Integer, Message> root_messages = new HashMap<Integer,Message>();
        for (MessageType msg : msgs){
            Message parent = all_messages.get(new Integer(msg.getFather()));
            Message child = all_messages.get(new Integer(msg.getMessageId()));
            if (parent != null){
                parent.getChild().add(child);
                child.setParent(parent);
            }
            else
                root_messages.put(new Integer(child.getMsg_id()), child);
        }

        return root_messages;
    }
/**
 * gets the users from the xml
 * @param allUsers - the list of all users type 
 * @return
 */
    private HashMap<String, User> getUsers(List<UserType> allUsers) {
        HashMap<String, User> users = new HashMap<String, User>();
        for (UserType user_data : allUsers){
            User user = new User();
            user.setUp(PermissionFactory.getUserPermission(user_data.getPermission()));
            Details details = new Details(user_data.getUsername(), user_data.getPassword(), user_data.getEmail(), user_data.getFirstName(), user_data.getLastName(), user_data.getAddress(), user_data.getGender());
            user.setDetails(details);
            users.put(user.getDetails().getUsername(), user);
        }
        return users;
    }
/**
 * gets the forum from xml to a object forum
 * where all the fields are initializeds
 * @return the forum as an object from xml
 */
    public Forum getForumFromXml() {
        FileInputStream in = null;
        Forum forum = null;
	try {

            JAXBContext jc = JAXBContext.newInstance("forum.server.persistencelayer");
            Unmarshaller u = jc.createUnmarshaller();

            in = new FileInputStream("forum.xml");

            // Obtain the data from the XML file.
            ForumType data_forum = (ForumType)u.unmarshal(in);

            forum = new Forum();
            Message.setGensym(new Integer(data_forum.getNumOfMsgs()));
            HashMap<String,User> users = getUsers(data_forum.getAllUsers());
            HashMap<Integer,Message> messages = getMsgs(data_forum.getAllMessages(), users);
            forum.setMessages(messages);
            forum.setRegistered(users);
        }
          catch (JAXBException e) {
            e.printStackTrace();
	} catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
	} catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
	}
	finally {
	//	System.exit(0);
	}
        return forum;
    }

    /**
     * adds to the xml a registerd user
     * @param username - nick name
     * @param password
     * @param email
     * @param firstname
     * @param lastname
     * @param address
     * @param gender - male or female
     * @param up - user permission, the authorization the user has
     */
    public void addRegUserToXml(String username, String password, String email, String firstname, String lastname, String address, String gender, String up) {
        UserType data_user = new UserType();
        FileInputStream in = null;
        FileOutputStream out = null;
        Forum forum = null;
	try {

           JAXBContext jc = JAXBContext.newInstance("forum.server.persistencelayer");
            Unmarshaller u = jc.createUnmarshaller();

            in = new FileInputStream("forum.xml");

            // Obtain the data from the XML file.
            ForumType data_forum = (ForumType)u.unmarshal(in);

            data_user.setUsername(username);
            data_user.setPassword(password);
            data_user.setEmail(email);
            data_user.setFirstName(firstname);
            data_user.setLastName(lastname);
            data_user.setAddress(address);
            data_user.setGender(gender);
            data_user.setPermission(up);

            data_forum.getAllUsers().add(data_user);
            Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // Write al; the data back to the XML file.
            out = new FileOutputStream("forum.xml");
	    m.marshal(data_forum,out);
            out.close();


        }
          catch (JAXBException e) {
            e.printStackTrace();
	} catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
	} catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
	}
	finally {
		//System.exit(0);
	}
    }
/**
 * adds a message to the xml
 * @param sbj - the subject
 * @param cont - the content
 * @param msg_id - the message id
 * @param parent_id
 * @param username
 * @param datetime
 */
    public void addMsgToXml(String sbj, String cont, int msg_id, int parent_id, String username, Date datetime) {
        MessageType data_msg = new MessageType();
        FileInputStream in = null;
        FileOutputStream out = null;
	try {

        JAXBContext jc = JAXBContext.newInstance("forum.server.persistencelayer");
            Unmarshaller u = jc.createUnmarshaller();

                   in = new FileInputStream("forum.xml");

            // Obtain the data from the XML file.
            ForumType data_forum = (ForumType)u.unmarshal(in);

            data_msg.setContent(cont);
            data_msg.setCreator(username);
            GregorianCalendar c = new GregorianCalendar();
            c.setTime(datetime);
            XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
            data_msg.setDate(date2);
            data_msg.setFather(parent_id);
            data_msg.setMessageId(msg_id);
            data_msg.setSubject(sbj);

            data_forum.setNumOfMsgs(Message.getGensym().intValue());
            data_forum.getAllMessages().add(data_msg);
            Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // Write al; the data back to the XML file.
            out = new FileOutputStream("forum.xml");
	    m.marshal(data_forum,out);
            out.close();


        }
        catch (DatatypeConfigurationException ex) {
            ex.printStackTrace();
        }          catch (JAXBException e) {
            e.printStackTrace();
	} catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
	} catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
	}
	finally {
	//	System.exit(0);
	}
    }
/**
 * modify a msg in the xml
 * @param id_toChange - the message id we want to change
 * @param newCont - the new content
 */
    public void modifyMsgInXml(int id_toChange, String newCont) {
        FileInputStream in = null;
        FileOutputStream out = null;
	try {

   JAXBContext jc = JAXBContext.newInstance("forum.server.persistencelayer");
            Unmarshaller u = jc.createUnmarshaller();

                        in = new FileInputStream("forum.xml");

            // Obtain the data from the XML file.
            ForumType data_forum = (ForumType)u.unmarshal(in);
            List<MessageType> msgs = data_forum.getAllMessages();
            for (MessageType msg : msgs){
                if (msg.getMessageId() == id_toChange){
                    msg.setContent(newCont);
                }
            }

            Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // Write al; the data back to the XML file.
            out = new FileOutputStream("forum.xml");
	    m.marshal(data_forum,out);
            out.close();


        }
          catch (JAXBException e) {
            e.printStackTrace();
	} catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
	} catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
	}
	finally {
		//System.exit(0);
	}
    }//class

    public void changeUserPermission(String username, String permission) {
        FileInputStream in = null;
        FileOutputStream out = null;
	try {

            JAXBContext jc = JAXBContext.newInstance("forum.server.persistencelayer");
            Unmarshaller u = jc.createUnmarshaller();

                        in = new FileInputStream("forum.xml");

            // Obtain the data from the XML file.
            ForumType data_forum = (ForumType)u.unmarshal(in);
            List<UserType> users = data_forum.getAllUsers();
            for (UserType user : users){
                if (user.getUsername().equals(username)){
                    user.setPermission(permission);
                }
            }

            Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // Write al; the data back to the XML file.
            out = new FileOutputStream("forum.xml");
	    m.marshal(data_forum,out);
            out.close();


        }
          catch (JAXBException e) {
            e.printStackTrace();
	} catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
	} catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
	}
	finally {
		//System.exit(0);
	}
    }





}
