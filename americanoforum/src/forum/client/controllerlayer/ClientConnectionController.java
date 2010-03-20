package forum.client.controllerlayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.StringTokenizer;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import forum.Settings;
import forum.server.domainlayer.User;
import forum.tcpcommunicationlayer.*;
import java.util.logging.Level;

/**
 * This class handles the communication between the client and the server.
 * 
 * @author Tomer Heber
 */
public class ClientConnectionController extends Thread {

	private Socket m_socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
        private String _user;
	
	private static Logger log = Logger.getLogger(Settings.loggerName);
	
	public ClientConnectionController(String addr, short port) throws IOException {
		InetAddress ia = InetAddress.getByName(addr);
		connect(ia,port);
                _user= null;

	}
	
	@Override
	public void run() {
		BufferedReader prompt = new BufferedReader(new InputStreamReader(System.in));  
		try {
			printHelp();
			while (true) {
				/* Receives a command from the user. */
				System.out.print("> ");
				String str = prompt.readLine();
				this.log.log(Level.INFO, "read line from user");
				if (str == null) {
					break;
				}
				
				if (str.equals("disconnect")) {
					break;
				}
				
				if (str.equals("help")) {
					printHelp();
					continue;
				}
								
				ClientMessage msg;
				try {
					/* Handles the command. */
					msg = handleCommand(str);						
				} catch (BadCommandException e) {
					log.info("The user has inputed an invalid command.");
					e.printStackTrace();
					System.out.println();
					continue;
				}
				/* send the message to the server. */
				out.writeObject(msg);
                                this.log.log(Level.INFO, "sent command from client");
				/* receive response from the server. */
				Object o = in.readObject();
                                this.log.log(Level.INFO, "recieved response from server");
				if (o == null) {
					log.severe("Lost connection to server.");
					break;
				}
				if (!(o instanceof ServerResponse)) {
					log.severe("Received an invalid response from server.");
					break;
				}
				
				ServerResponse res = (ServerResponse)o;
				/* Check if the server has done the command. */
				if (res.hasExecuted()) {
					System.out.println("done!");
				}
				else {
					System.out.println("failed!");
				}
				/* Print the response from the server */
			
                                if (msg instanceof LoginMessage){
                                    this._user = res.getResponse();
                                    log.log(Level.INFO,"Changed username to "+res.getResponse());
                                }
                                else if (msg instanceof ViewForumMessage){
                                    	System.out.println(res.getResponse());
                                }
                                 else if (msg instanceof LogoffMessage){
                                      this._user = null;
                                    log.log(Level.INFO,"Changed username to null");
                                }
                                else if (msg instanceof RegisterMessage){
                                    if (res.getResponse().equals("")){
                                          log.log(Level.INFO,"didn't change the user in the client");
                                    }
                                    else{
                                      this._user = res.getResponse();

                                    log.log(Level.INFO,"Changed username to "+this._user);
                                }
                                }
			}
		} catch (ClassNotFoundException e) {
			log.severe("Received an invalid object from the server.");
			e.printStackTrace();
		} catch (IOException e) {
			log.severe("IOException occured while trying to read/send/write.");
			e.printStackTrace();			
		}
                catch (Exception e){
                    log.severe("unknown exception catched: \n");
                    e.printStackTrace();
                }
		finally {			
			try {
				/* Closing all the streams. */
				prompt.close();
				closeConnection();
			} catch (IOException e) {
				log.severe("IOException while trying to close streams.");
				e.printStackTrace();
			}			
		}
	}
	
	private void printHelp() {
		System.out.println(
				"help menu:" + "\n" +
				"- help " +  "\n" +
				"- add_message <subject> <message content>" + "\n" +
				"- add_reply <message id to reply to> <subject> <message content>" + "\n" +
				"- modify_message <message id to modify>  <new message content>" + "\n" +
				"- view_forum" + "\n" +
				"- logoff" + "\n" +
				"- login <username> <password>" + "\n" +
				"- register <username> <password> <email> <first> <last> <address> <gender>" + "\n" +
                                "- delete_message <message id to delete>\n"+
                                "- promote <userName to promote>"+"\n" +
				"- disconnect" + "\n" +
				"//TODO add more operations (Admin, Moderator, Search)"	+ "\n"			
		);								
		
	}

	/**
	 * This method receives commands and creates the proper message to send to the server.
	 * 
	 * @param str The command to do.
	 * @return The message to send back to the server.
	 * @throws BadCommandException This exception is thrown is the command given by the user is invalid.
	 */
	private ClientMessage handleCommand(String str) throws BadCommandException {		
		try {
			StringTokenizer st = new StringTokenizer(str);
			String command = st.nextToken();
			this.log.log(Level.INFO,"handle command: the command is: "+command);
			if (command.equals("add_message")) {
                                String subject = st.nextToken();
                                String message = str.substring(command.length()+subject.length()+2);
				return new AddMessageMessage(subject,message,this.getUser());
			}
			if (command.equals("view_forum")) {
				return new ViewForumMessage();
			}
			if (command.equals("login")) {
                            String a=st.nextToken();
                            String b=st.nextToken();
				return new LoginMessage(a,b,this.getUser());
			}
			if (command.equals("logoff")) {
				return new LogoffMessage(this.getUser());
			}
			if (command.equals("register")) {
                                this.log.log(Level.INFO,"entered correct if: ");
                                String a=st.nextToken();
                                String b=st.nextToken();
                                String c=st.nextToken();
                                String d=st.nextToken();
                                String e=st.nextToken();
                                String f=st.nextToken();
                                String g=st.nextToken();
                                    this.log.log(Level.INFO,"entering constructor: ");
				return new RegisterMessage(a,b,c,d,e,f,g,this.getUser());
			}
			if (command.equals("add_reply")) {
				String messageIdS = st.nextToken();
                                String subject = st.nextToken();
                                String message = str.substring(command.length()+messageIdS.length()+subject.length()+3);
				int messageId = Integer.parseInt(messageIdS);
				return new AddReplyMessage(messageId,subject,message,this.getUser());
			}			
			if (command.equals("modify_message")) {
				String messageIdS = st.nextToken();
                                String message = str.substring(command.length()+messageIdS.length()+2);
				int messageId = Integer.parseInt(messageIdS);
				return new ModifyMessageMessage(messageId,message,this.getUser());
			}
                        if (command.equals("promote")) {
				String userName = st.nextToken(); 
                                return new PromoteMessage(userName,this.getUser());
			}
                         if (command.equals("delete_message")) {
				int idToDelate = Integer.parseInt(st.nextToken());
                                return new DeleteMessageMessage(idToDelate,this.getUser());
			}
			
			// TODO Add Search messages.
			// TODO Add Admin messages
			// TODO Add Moderator messages.
		}
		catch(Exception e) {
			throw new BadCommandException("The command "+str+" is invalid.");
		}
		
		throw new BadCommandException("The command "+str+" is unknown.");		
	}	

	private void connect(InetAddress addr, short port) throws IOException {
		SocketAddress sa = new InetSocketAddress(addr,port);
		/* Connect to the server */
		m_socket = new Socket();
		m_socket.connect(sa);
		out = new ObjectOutputStream(m_socket.getOutputStream());
		out.flush();
		in = new ObjectInputStream(m_socket.getInputStream());
		log.info("The client is connected to the server.");
	}
	
	private void closeConnection() throws IOException {
		in.close();
		out.close();
		m_socket.close();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String logFileName = "forumAmericano.log";
		if (args.length > 0) {
			logFileName = args[0];
		}
		try {
			/* Create a logger for the client (to a file...). */
			FileHandler handler = new FileHandler(logFileName,true);
			log.addHandler(handler);
		} catch (SecurityException e) {		
			e.printStackTrace();
		} catch (IOException e) {		
			e.printStackTrace();
		}
				
		try {
			/* Start the client */
			Thread thread = new ClientConnectionController("127.0.0.1",(short)1234);
			thread.start();
			try {
				thread.join();
			} catch (InterruptedException e) {				
				e.printStackTrace();
			}
		} catch (IOException e) {
			log.severe("An IOException was thrown while trying to connect to the server.");
			e.printStackTrace();
		}		
				
		log.info("Exiting...");

	}

    public String getUser() {
        return _user;
    }

}
