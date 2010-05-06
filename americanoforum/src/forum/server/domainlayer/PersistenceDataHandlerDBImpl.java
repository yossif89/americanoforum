/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package forum.server.domainlayer;

import forum.server.persistencelayer.*;
import java.lang.reflect.Array;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
        HashMap<String,User> users = getUsers();
        HashMap<Long,Message>[] messages = getMsgs(users);

        forum.setMessages(messages[0]);
        forum.setAllMessages(messages[1]);

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

    private UserDB getUser(String username) {
	Transaction tx = null;
	Session session = SessionFactoryUtil.getInstance().getCurrentSession();
        UserDB user = null;
	try {
            tx = session.beginTransaction();
            user = ((UserDB)session.get(UserDB.class, username));
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
        return user;
    }

    private MessageDB getMessage(long id) {
	Transaction tx = null;
	Session session = SessionFactoryUtil.getInstance().getCurrentSession();
        MessageDB msg = null;
	try {
            tx = session.beginTransaction();
            msg = ((MessageDB)session.get(MessageDB.class, id));
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
        msg.setCreator(username);
        msg.setDate(datetime);
        Long father = new Long(parent_id);
        msg.setFather(father);
        msg.setMessageId(msg_id);
        msg.setSubject(sbj);

        createMessage(msg);
    }

    public void modifyMsgInXml(long id_toChange, String newCont) {
        MessageDB msg = getMessage(id_toChange);
        msg.setContent(newCont);
        updateMessageDB(msg);
    }

    public void changeUserPermission(String username, String permission) {
        UserDB user = getUser(username);
        user.setPermission(permission);
        updateUserDB(user);
    }

    public void deleteMsgFromXml(long msg_id) {
        MessageDB msg = getMessage(msg_id);
        deleteMessageDB(msg);
    }

    private HashMap<String, User> getUsers() {
        Session session = SessionFactoryUtil.getInstance().getCurrentSession();
        List allUsers=null;
	String hql = "from users";
	Query queryRes = session.createQuery(hql);
        allUsers = queryRes.list();

        HashMap<String,User> usersRes = new HashMap<String, User>();
        for(int i=0; i<allUsers.size(); i++){
            UserDB user_data = (UserDB)allUsers.get(i);
            User user = new User();
            user.setUp(PermissionFactory.getUserPermission(user_data.getPermission()));
            Details details = new Details(user_data.getUsername(), user_data.getPassword(), user_data.getEmail(), user_data.getFirstName(), user_data.getLastName(), user_data.getAddress(), user_data.getGender());
            user.setDetails(details);
            usersRes.put(user.getDetails().getUsername(), user);
        }
        return usersRes;
    }

    //myArr[0] - rootMsgs, myArr[1] - allMsgs
    private HashMap<Long, Message>[] getMsgs(HashMap<String,User> allUsers) {
        HashMap<Long, Message> all_messages = new HashMap<Long,Message>();
        Session session = SessionFactoryUtil.getInstance().getCurrentSession();
        List allMsgs_data=null;
	String hql = "from messages";
	Query queryRes = session.createQuery(hql);
        allMsgs_data = queryRes.list();

        for (int i=0; i<allMsgs_data.size(); i++){
            MessageDB msg = (MessageDB)allMsgs_data.get(i);
            User creator = allUsers.get(msg.getCreator());
            Message newMsg = new Message(msg.getSubject(), msg.getContent(), creator);
            newMsg.setMsg_id(msg.getMessageId());
            newMsg.setDate(msg.getDate());
            all_messages.put(new Long(msg.getMessageId()), newMsg);
            creator.getMyMessages().put(new Long(newMsg.getMsg_id()), newMsg);
        }

        HashMap<Long,Message>[] myarr = (HashMap<Long,Message>[])Array.newInstance(HashMap.class,2);
        myarr[0]=new HashMap<Long,Message>();
        myarr[1]=new HashMap<Long,Message>();
        //for (int i=0; i<allMsgs_data; i++){
         //  Message parent = all_messages.get(new Long(msg.getFather()));
          //  Message child = all_messages.get(new Long(msg.getMessageId()));
    /*        if (parent != null){
                parent.getChild().add(child);
                child.setParent(parent);
                myarr[1].put(new Long(child.getMsg_id()), child);
            }
            else{
                myarr[0].put(new Long(child.getMsg_id()), child);
                myarr[1].put(new Long(child.getMsg_id()), child);
            }
        //}*/
        return myarr;
    }





}
