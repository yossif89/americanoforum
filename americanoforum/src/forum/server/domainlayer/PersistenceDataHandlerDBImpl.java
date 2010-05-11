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
import java.util.Set;
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

        //finding max index for msgs
        Set<Long> msgs_ids = messages[1].keySet();
        long max = 1;
        for(Long id : msgs_ids){
            if (id.longValue() > max)
                max = id.longValue();
        }
        Message.setGensym(max + 1);
        forum.setRegistered(users);
        forum.updateSearchEngine();
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
       // SessionFactoryUtil.close();
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
        //SessionFactoryUtil.close();
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
       // SessionFactoryUtil.close();
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
      //  SessionFactoryUtil.close();
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
          //      SessionFactoryUtil.close();
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
    //        SessionFactoryUtil.close();
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
     //   SessionFactoryUtil.close();
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
        Long father = null;
        if (parent_id != -1)
            father = new Long(parent_id);
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
        Transaction tx = null;
        List allUsers=null;
	Session session = SessionFactoryUtil.getInstance().getCurrentSession();
	try {
		tx = session.beginTransaction();
		String hql = "from UserDB";
                Query queryRes = session.createQuery(hql);
                //tx.commit();
                allUsers = queryRes.list();
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
   //     SessionFactoryUtil.close();

        HashMap<String,User> usersRes = new HashMap<String, User>();
        int size = 0;
        if (allUsers != null)
            size = allUsers.size();
        System.out.println("size ="+size);
        for(int i=0; i<size; i++){
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
        Transaction tx = null;
        Session session = SessionFactoryUtil.getInstance().getCurrentSession();
        List allMsgs_data=null;
        try {
		tx = session.beginTransaction();
		String hql = "from MessageDB";
                Query queryRes = session.createQuery(hql);
		//tx.commit();
                allMsgs_data = queryRes.list();
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
        //SessionFactoryUtil.close();

        HashMap<Long, Message> all_messages = new HashMap<Long,Message>();
        int size = 0;
        if(allMsgs_data != null)
            size = allMsgs_data.size();
        for (int i=0; i<size; i++){
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
        for (int i=0; i<size; i++){
            Message parent=null;
            Message child=null;
            Long parent_id = ((MessageDB)allMsgs_data.get(i)).getFather();
            Long child_id = ((MessageDB)allMsgs_data.get(i)).getMessageId();
            if(parent_id != null)
                parent = all_messages.get(parent_id);
            if(child_id != null)
                child = all_messages.get(child_id);
            if (parent != null){
                parent.getChild().add(child);
                child.setParent(parent);
                myarr[1].put(new Long(child.getMsg_id()), child);
            }
            else{
                myarr[0].put(new Long(child.getMsg_id()), child);
                myarr[1].put(new Long(child.getMsg_id()), child);
            }
        }
        return myarr;
    }





}
