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
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Yossi
 */
public class PersistenceDataHandlerImpl implements PersistenceDataHandler {

   // private forum.server.persistencelayer.Forum _forum; // the data forum

   // public PersistenceDataHandlerImpl(){
    //    this._forum=null;
   // }

    //creates all the messages and adds them to the users. not links the msgs to their parents
    private HashMap<Integer, Message> getMsgs(List<MessageType> msgs, HashMap<String,User> users){
        HashMap<Integer, Message> messages = new HashMap<Integer,Message>();
        for (MessageType msg : msgs){
            Message newMsg = new Message(msg.getSubject(), msg.getContent(), users.get(msg.getCreator()));
            messages.put(new Integer(msg.getMessageId()), newMsg);


        }
    }

    public Forum getForumFromXml() {
        FileInputStream in = null;
	FileOutputStream out = null;

	try {

            JAXBContext jc = JAXBContext.newInstance("forum.server.domainlayer");
            Unmarshaller u = jc.createUnmarshaller();

            in = new FileInputStream("forum.xml");

            // Obtain the data from the XML file.
            ForumType data_forum = (ForumType)u.unmarshal(in);

            Forum forum = new Forum();

            for (MessageType msg : msgs){
               Message newMsg = new Message(msg.getSubject(), msg.getContent(), msg.getContent());
                forum.addMessage(msg.getSubject(), msg.getContent(), msg.get);
            }
        }


    }

    public void addRegUserToXml(String username, String password, String email, String firstname, String lastname, String address, String gender, String up) {
        
    }

    public void addMsgToXml(String sbj, String cont, int msg_id, int parent_id, String username, Date datetime) {
        MessageType msg = new MessageType();
	msg.setContent(cont);
	msg.setAuthor(username);
        msg.setSubject(sbj);
        msg.setMsgId(msg_id);
        
    }

    public void modifyMsgInXml(int id_toChange, String newCont) {
        throw new UnsupportedOperationException("Not supported yet.");
    }



}
