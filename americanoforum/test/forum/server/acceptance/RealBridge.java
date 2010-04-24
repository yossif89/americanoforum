package forum.server.acceptance;

//There is no real implementation yet so no real connection making was possible!

import forum.client.controllerlayer.ClientConnectionController;
import forum.client.controllerlayer.ControllerHandler;
import forum.client.controllerlayer.ControllerHandlerFactory;
import forum.client.controllerlayer.ControllerHandlerImpl;
import forum.server.controllerlayer.ServerConnectionController;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JButton;

//The class is build for compiling only.

/**
 * This class Is simulating the case in which a real implementation exists ,
 * and if so , this real bridge connects the real implementation of the project to the
 * user acceptance tests.
 */
public class RealBridge implements ForumBridge {

       ClientConnectionController _driver;

	public RealBridge(){
            try{

              _driver = new ClientConnectionController("127.0.0.1", (short)1234);
            }
            catch(Exception e){
                _driver = null;
                System.out.println("failed communication with the server");
            }
		build();
	}

	public void build(){
                Object[] args = new Object[7];
                args[0]= "iluxa13";
                args[1]= "abcd";
                args[2]= "ilya";
                args[3]= "g"   ;
                args[4]= "@";
                args[5]= "Haifa";
                args[6]= "male";
               _driver.communicate("register", args);
	}

//The explanations(javadoc) for each method exists in the interface AutomatingFormBridge

        public boolean login(String username , String password) {
                Object[] args = new Object[2];
                args[0]= username;
                args[1]= password;
                String ans = _driver.communicate("login", args);
                return (ans.equals(username));
	}

	public boolean register( String username, String password, String first, String last, String email, String address, String gender){
                 Object[] args = new Object[7];
                args[0]= username;
                args[1]= password;
                args[2]= first;
                args[3]= last;
                args[4]= email;
                args[5]= address;
                args[6]= gender;
                String ans = _driver.communicate("register", args);
                return (ans.equals(username));
	}

	



}
