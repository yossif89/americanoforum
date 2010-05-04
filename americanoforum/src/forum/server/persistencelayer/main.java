package forum.server.persistencelayer;


import forum.server.persistencelayer.MessageDB;
import forum.server.persistencelayer.SessionFactoryUtil;
import forum.server.persistencelayer.UserDB;
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

        System.out.println("username: " + test.getUsername());
        System.out.println("address: " + test.getAddress());
        System.out.println("email: " + test.getEmail());
        System.out.println("first: " + test.getFirstName());
        System.out.println("last: " + test.getLastName());
        System.out.println("pass: " + test.getPassword());
        System.out.println("permission: " + test.getPermission());

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
