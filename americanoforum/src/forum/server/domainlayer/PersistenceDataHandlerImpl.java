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


/**
 *
 * @author Yossi
 */
public class PersistenceDataHandlerImpl implements PersistenceDataHandler {

   // private forum.server.persistencelayer.Forum _forum; // the data forum

   // public PersistenceDataHandlerImpl(){
    //    this._forum=null;
   // }

    public Forum getForumFromXml() {
        FileInputStream in = null;
	FileOutputStream out = null;

	try {

            JAXBContext jc = JAXBContext.newInstance("forum.server.domainlayer");
            Unmarshaller u = jc.createUnmarshaller();

            in = new FileInputStream("forum.xml");

            // Obtain the data from the XML file.
            forum.server.persistencelayer.Forum forum = (forum.server.persistencelayer.Forum)u.unmarshal(in);



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
