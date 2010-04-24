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
import java.lang.UnsupportedOperationException;
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

	public static Logger log = Logger.getLogger(Settings.loggerName);

	public ClientConnectionController(String addr, short port) throws IOException {
            String logFileName = "forumAmericano.log";
				try {
					/* Create a logger for the client (to a file...). */
					FileHandler handler = new FileHandler(logFileName,true);
					log.addHandler(handler);
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
		InetAddress ia = InetAddress.getByName(addr);
		connect(ia,port);
		_user= "";
	}

	public String communicate(String operation,Object[] args){
            ServerResponse res=null;
		ClientMessage msg=null;
		try {
			/* Handles the command. */
			msg = handleCommand(operation,args);

			out.writeObject(msg);
			this.log.log(Level.INFO, "sent command from client");
			/* receive response from the server. */
			Object o = in.readObject();
			this.log.log(Level.INFO, "recieved response from server");
			if (o == null) {
                              
				log.severe("Lost connection to server.");
				return "";
			}
			if (!(o instanceof ServerResponse)) {
				log.severe("Received an invalid response from server.");
				return "";
			}
			res = (ServerResponse)o;
                        if (res.getEx() != null){
                       
                        }
			if (res.hasExecuted()) {
                            log.info("server succeded handling the client message");

                            if (msg instanceof LoginMessage){
                                    this._user = res.getResponse();
                                    log.log(Level.INFO,"Changed username to "+res.getResponse());
                            }
                            else if (msg instanceof LogoffMessage){
                                    this._user = "null";
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
                              return res.getResponse();
		}
                        else{
                          if ((res.getEx() instanceof UnsupportedOperationException) ||(res.getEx() instanceof IllegalArgumentException)||(res.getEx() instanceof IllegalAccessException)){
                                 if (msg instanceof LoginMessage){
                                    return "$One of your details is not correct";
                            }
                            else if (msg instanceof LogoffMessage){
                                      return "$You are not logged in";
                            }
                            else if (msg instanceof RegisterMessage){
                                   return "$Your username is already in use, try another one";
                            }
                            else
                              return "$You don't have the permission to perform this operation \n Try logging in or contact the administrator";
                          }
                         
                        
                        }
                }
                        catch(Exception e){
                                      log.log(Level.SEVERE,"exception in the connection controller");
                        }

                return "";
                }
                

	


		/**
		 * This method receives commands and creates the proper message to send to the server.
		 *
		 * @param str The command to do.
		 * @return The message to send back to the server.
		 * @throws BadCommandException This exception is thrown is the command given by the user is invalid.
		 */
		private ClientMessage handleCommand(String command,Object[] args) throws BadCommandException {
			try {
	
				this.log.log(Level.INFO,"handle command: the command is: "+command);
				if (command.equals("add_message")) {
					String subject =(String)args[0];
					String message = (String)args[1];
					return new AddMessageMessage(subject,message,this.getUser());
				}
				if (command.equals("view_forum")) {
					return new ViewForumMessage();
				}
				if (command.equals("login")) {
					String a=(String)args[0];
					String b=(String)args[1];
					return new LoginMessage(a,b,this.getUser());
				}
				if (command.equals("logoff")) {
					return new LogoffMessage(this.getUser());
				}
				if (command.equals("register")) {
					this.log.log(Level.INFO,"entered correct if: ");
					String a=(String)args[0];
					String b=(String)args[1];
					String c=(String)args[2];
					String d=(String)args[3];
					String e=(String)args[4];
					String f=(String)args[5];
					String g=(String)args[6];
					this.log.log(Level.INFO,"entering constructor: ");
					return new RegisterMessage(a,b,c,d,e,f,g);
				}
				if (command.equals("add_reply")) {
					String subject = (String)args[1];
					String message = (String)args[2];
					long messageId = (Long)args[0];
					return new AddReplyMessage(messageId,subject,message,this.getUser());
				}
				if (command.equals("modify_message")) {
					String message = (String)args[1];
					long messageId = (Long)args[0];
					return new ModifyMessageMessage(messageId,message,this.getUser());
				}
				if (command.equals("promote")) {
					String userName = (String)args[0];
					return new PromoteMessage(userName,this.getUser());
				}
				if (command.equals("delete_message")) {
					long idToDelate = (Long)args[0];
					return new DeleteMessageMessage(idToDelate,this.getUser());
				}
				if (command.equals("searchByAuthor")) {
					String username = (String)args[0];
					int fromInd = Integer.parseInt((String)args[0]);
					int toInd = Integer.parseInt((String)args[1]);
					return new SearchByAuthorMessage(username, fromInd, toInd);
				}
				if (command.equals("searchByContent")) {
					String from = (String)args[0];
					String to = (String)args[1];
					int fromInd = Integer.parseInt(from);
					int toInd = Integer.parseInt(to);
					String toSearch = (String)args[2];
					return new SearchByContentMessage(toSearch, fromInd, toInd);
				}

				// TODO Add Search messages.
				// TODO Add Admin messages
				// TODO Add Moderator messages.
			}
			catch(Exception e) {
				throw new BadCommandException("The command "+command+" is invalid.");
			}

			throw new BadCommandException("The command "+command+" is unknown.");
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

		//	/**
		//	 * @param args
		//	 */
		//	public static void main(String[] args) {
		//		String logFileName = "forumAmericano.log";
		//		if (args.length > 0) {
		//			logFileName = args[0];
		//		}
		//		try {
		//			/* Create a logger for the client (to a file...). */
		//			FileHandler handler = new FileHandler(logFileName,true);
		//			log.addHandler(handler);
		//		} catch (SecurityException e) {
		//			e.printStackTrace();
		//		} catch (IOException e) {
		//			e.printStackTrace();
		//		}
		//
		//		try {
		//			/* Start the client */
		//			Thread thread = new ClientConnectionController("127.0.0.1",(short)1234);
		//			thread.start();
		//			try {
		//				thread.join();
		//			} catch (InterruptedException e) {
		//				e.printStackTrace();
		//			}
		//		} catch (IOException e) {
		//			log.severe("An IOException was thrown while trying to connect to the server.");
		//			e.printStackTrace();
		//		}
		//
		//		log.info("Exiting...");
		//
		//	}

		public String getUser() {
			return _user;
		}

	}
