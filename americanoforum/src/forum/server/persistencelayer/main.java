package forum.server.persistencelayer;


import forum.server.persistencelayer.MessageDB;
import forum.server.persistencelayer.SessionFactoryUtil;
import forum.server.persistencelayer.UserDB;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Yossi
 */
public class main {

    private static void createMessage(MessageDB msg) {
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

    private static void createUser(UserDB user) {
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

    private static UserDB getUser(String username) {
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

    private static MessageDB getMessage(long id) {
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

    public static void main(String[] args){
        UserDB user = new UserDB();
        user.setAddress("aaa");
        user.setEmail("aaa");
        user.setFirstName("yossi");
        user.setGender("male");
        user.setLastName("f");
        user.setPassword("1111");
        user.setPermission("Admin");
        user.setUsername("felberba");
        
        createUser(user);

        UserDB test = getUser("felberba");

        MessageDB msg = new MessageDB();
        msg.setContent("bla bla bla");
        msg.setCreator(test.getUsername());
        msg.setDate(new Date());
        msg.setFather(null);
        msg.setMessageId(2);
        msg.setSubject("hello");

        createMessage(msg);

        System.out.println("username: " + test.getUsername());
        System.out.println("address: " + test.getAddress());
        System.out.println("email: " + test.getEmail());
        System.out.println("first: " + test.getFirstName());
        System.out.println("last: " + test.getLastName());
        System.out.println("pass: " + test.getPassword());
        System.out.println("permission: " + test.getPermission());


        MessageDB test2 = getMessage(2);

        System.out.println("content: " + test2.getContent());
        System.out.println("creator: " + test2.getCreator());
        System.out.println("equals? "+(test2.getCreator().equals(test)));
        System.out.println("date: " + test2.getDate());
        System.out.println("father: " + test2.getFather());
        System.out.println("id: " + test2.getMessageId());
        System.out.println("subject: " + test2.getSubject());

        /*MessageDB message1 = new MessageDB();
        message1.setContent("bla bla");
        message1.setCreator("yossi");
        message1.setDate(null);
        message1.setFather(message1);
        message1.setMessageId(messageId);
        message1.setSubject(null);

        createMessage(message1);*/
    }

}
