/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package forum.server.domainlayer;

import forum.server.persistencelayer.*;
import java.util.Date;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Yossi
 */
public class PersistenceDataHandlerDBImpl implements PersistenceDataHandler{

    public Forum getForumFromXml() {
        Forum forum = new Forum();


        return forum;
    }

    private void createUser(UserDB user) {
        Transaction tx = null;

	Session session = SessionFactoryUtil.getInstance().getCurrentSession();
	try {
		tx = session.beginTransaction();
		session.save(user);
		tx.commit();
	} catch (RuntimeException e) {
		if (tx != null && tx.isActive()) {
			try {
				// Second try catch as the rollback could fail as well
				tx.rollback();
			} catch (HibernateException e1) {
			// add logging
			}
			// throw again the first exception
			throw e;
		}
	}
    }

    private void createMessage(MessageDB msg) {
        Transaction tx = null;

	Session session = SessionFactoryUtil.getInstance().getCurrentSession();
	try {
		tx = session.beginTransaction();
		session.save(msg);
		tx.commit();
	} catch (RuntimeException e) {
		if (tx != null && tx.isActive()) {
			try {
				// Second try catch as the rollback could fail as well
				tx.rollback();
			} catch (HibernateException e1) {
			// add logging
			}
			// throw again the first exception
			throw e;
		}
	}
    }

    private UserDB getUserDB(String username) {

	Session session = SessionFactoryUtil.getInstance().getCurrentSession();		
        UserDB user = (UserDB)session.get(UserDB.class,username);
	return user;
    }

    private MessageDB getMessageDB(long parent_id) {
        Session session = SessionFactoryUtil.getInstance().getCurrentSession();
        MessageDB msg = (MessageDB)session.get(MessageDB.class,parent_id);
	return msg;
    }

    private void updateMessageDB(MessageDB msg) {
		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		try {
			tx = session.beginTransaction();
			session.update(msg);
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
				// add logging
				}
				// throw again the first exception
				throw e;
			}
		}
	}
     private void updateUserDB(UserDB user) {
            Transaction tx = null;
            Session session = SessionFactoryUtil.getInstance().getCurrentSession();
            try {
                tx = session.beginTransaction();
		session.update(user);
		tx.commit();
            } catch (RuntimeException e) {
                if (tx != null && tx.isActive()) {
                    try {
                    // Second try catch as the rollback could fail as well
                        tx.rollback();
                    } catch (HibernateException e1) {
                    // add logging
                    }
                    // throw again the first exception
                    throw e;
		}
            }
     }

     private void deleteMessageDB(MessageDB msg) {
	Transaction tx = null;
	Session session = SessionFactoryUtil.getInstance().getCurrentSession();
	try {
		tx = session.beginTransaction();
		session.delete(msg);
		tx.commit();
	} catch (RuntimeException e) {
		if (tx != null && tx.isActive()) {
			try {
				// Second try catch as the rollback could fail as well
				tx.rollback();
			} catch (HibernateException e1) {
			// add logging
			}
			// throw again the first exception
			throw e;
		}
	}
     }

    public void addRegUserToXml(String username, String password, String email, String firstname, String lastname, String address, String gender, String up) {
        UserDB user = new UserDB();
        user.setAddress(address);
        user.setEmail(email);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setGender(gender);
        user.setPassword(password);
        user.setPermission(up);
        user.setUsername(username);

        createUser(user);
        
    }

    public void addMsgToXml(String sbj, String cont, long msg_id, long parent_id, String username, Date datetime) {
        MessageDB msg = new MessageDB();
        msg.setContent(cont);
        UserDB creator = getUserDB(username);
        msg.setCreator(creator);
        msg.setDate(datetime);
        MessageDB father = getMessageDB(parent_id);
        msg.setFather(father);
        msg.setMessageId(msg_id);
        msg.setSubject(sbj);

        createMessage(msg);
    }

    public void modifyMsgInXml(long id_toChange, String newCont) {
        MessageDB msg = getMessageDB(id_toChange);
        msg.setContent(newCont);
        updateMessageDB(msg);
    }

    public void changeUserPermission(String username, String permission) {
        UserDB user = getUserDB(username);
        user.setPermission(permission);
        updateUserDB(user);
    }

    public void deleteMsgFromXml(long msg_id) {
        MessageDB msg = getMessageDB(msg_id);
        deleteMessageDB(msg);
    }





}
