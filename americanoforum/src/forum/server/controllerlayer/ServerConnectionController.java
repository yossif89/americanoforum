package forum.server.controllerlayer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

import forum.Settings;
import java.util.logging.FileHandler;
import java.util.logging.Level;

/**
 * This class runs the server (currently set to port 1234).
 * 
 * @author Tomer Heber
 */
public class ServerConnectionController extends Thread {
		

    private static Logger logger;

	@Override
	public void run() {
		short port = 1234;
		
		try {
			ServerSocket ss = new ServerSocket(port);
			logger.info("Server has started running on port "+port+".");
			while (true) {
				Socket s = ss.accept();
				logger.info("A connection was accepted from: "+s.getInetAddress()+".");
				ServerSingleConnectionController.startConnection(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.severe("An error has accoured in the server (IOException).");
		}
		
		logger.info("Server has closed.");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {		
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


		Thread t = new ServerConnectionController();
		/* Start the thread */
		t.start();
		try {
			/* Wait for the thread to finish running */
			t.join();
		} catch (InterruptedException e) {			
			e.printStackTrace();
			
		}
				
	}

}
