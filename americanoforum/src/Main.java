
import forum.server.domainlayer.*;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static  Logger logger;
    private static User curr_user;
    private static Forum forum;
/**
 * @param vect - the current vector of messages, if it is null so it is the root message
 * @param curr_msg - the current msg that the user is view
 */
    public static void view__and_handle_messages(Vector<Message> vect, Message curr_msg){
                    System.out.println("Please enter the ID of the message: ");
                    Scanner scanner = new Scanner(System.in);
                    int msg_ind=scanner.nextInt();
                    Message msg=null;
                    if (vect == null) 
                        msg = forum.getMessages().get(new Integer(msg_ind));
                    else{
                        for (int i=0; i<vect.size(); i++){
                            if (msg_ind == vect.elementAt(i).getMsg_id())
                                msg = vect.elementAt(i);
                        }
                    }
                    System.out.println("Subject: "+msg.getSubject());
                    System.out.println("Date: "+msg.getDate());
                    System.out.println("Creator: "+msg.getCreator().getDetails().getUsername());
                    System.out.println("Content: "+msg.getContent());
                    System.out.println("Replies: ");
                    String reply = "";
                    Vector<Message> reply_vect = msg.getChild();
                    for (int i=0; i<reply_vect.size(); i++){
                        reply = reply + reply_vect.elementAt(i).getSubject() + "    msg_id: " + reply_vect.elementAt(i).getMsg_id() + "\n";
                    }
                    System.out.println(reply);
                    System.out.println(" ");
                    System.out.println("Please select one of the following options: ");
                    System.out.println("[1] view reply.");
                    System.out.println("[2] add reply to current message (and return to main menu)");
                    System.out.println("[3] return to main menu.");
                    int selection=scanner.nextInt();
                    if (selection == 1){
                        view__and_handle_messages(msg.getChild(), msg);
                        return;
                    }
                    if(selection == 2){
                        System.out.println("Please enter a subject to your message: ");
                        String subject=scanner.next() + scanner.nextLine();
                        System.out.println("Please enter the content of your message: ");
                        String content=scanner.next()+scanner.nextLine();
                        forum.addReply(subject, content, curr_user, msg);
                        return;
                    }
                    else{
                        return;
                    }

    }

    public static void main(String[] args){
        try {
        // Create an appending file handler
        boolean append = true;
        FileHandler handler = new FileHandler("forumAmericano.log", append);

        // Add to the desired logger
        logger = Logger.getLogger("americanoforum");
        logger.addHandler(handler);
    } catch (IOException e) {
        /////how to log exception in creating logger?
    }
 logger.setLevel(Level.ALL);
 

        curr_user=null;
        int mode=0;//mode 0 for guest, mode 1 for user
        PersistenceDataHandler pipe = new PersistenceDataHandlerImpl();
        forum = pipe.getForumFromXml();

        System.out.println("******* Welcome To Americano-Forum *******");
        System.out.println("***** Where Coffee is just an Excuse *****");
        System.out.println(" ");

        while (true){
           if(mode ==0){
                System.out.println("Please select one of the following options: ");
                System.out.println("[1] view forum.");
                System.out.println("[2] register.");
                System.out.println("[3] log in.");
                System.out.println("[4] exit.");

                Scanner scanner = new Scanner(System.in);
                int choice = scanner.nextInt();

                if (choice == 1){
                    System.out.println(forum);
                }
                if (choice ==2){;
                    System.out.println("Please enter your username: ");
                    String username=scanner.next();
                    //while (forum.getRegisteredUsers().containsKey(username)){
                      //  System.out.println("Username is taken. Please enter another username: ");
                       // username=scanner.nextLine();
                   // }
                    System.out.println("Please enter your password: ");
                    String password=scanner.next();
                    System.out.println("Please enter your email: ");
                    String email=scanner.next();
                    System.out.println("Please enter your first name: ");
                    String first_name=scanner.next();
                    System.out.println("Please enter your last name: ");
                    String last_name=scanner.next();
                    System.out.println("Please enter your address: ");
                    String address=scanner.next();
                    System.out.println("Please enter your gender [male/female]: ");
                    String gender=scanner.next();
                    curr_user=new User();
                    forum.register(curr_user,username, password, email, first_name, last_name, address, gender);
                    mode=1;
                }
                if (choice ==3){
                    System.out.println("Please enter your username: ");
                    String username=scanner.next();
                    System.out.println("Please enter your password: ");
                    String password=scanner.next();
                    curr_user=forum.login(username, password);
                    mode=1;
                }
                if(choice==4){
                    System.exit(0);
                }
            }
           if (mode==1){
               System.out.println("************** Logged In **************");
               System.out.println("Hello "+curr_user.getDetails().getUsername()+"!");
               System.out.println("Please select one of the following options: ");
               System.out.println("[1]  view forum");
               System.out.println("[2]  add new message.");
               System.out.println("[3] view message.");
               System.out.println("[4] logout.");
               System.out.println("[5] exit.");

                Scanner scanner = new Scanner(System.in);
                int choice = scanner.nextInt();

                if (choice==1){
                    System.out.println(forum);
                }
                if (choice==2){
                    System.out.println("Please enter a subject to your message: ");
                    String subject=scanner.next() + scanner.nextLine();
                    System.out.println("Please enter the content of your message: ");
                    String content= scanner.next() + scanner.nextLine();
                    forum.addMessage(subject, content, curr_user);
                }
                if (choice==3){
                    view__and_handle_messages(null,null);
                }
                if (choice==4){
                    mode=0;
                }
                if (choice==5){
                    System.exit(0);
                }
           }
        }

    }
}//class
