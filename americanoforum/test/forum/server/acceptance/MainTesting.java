package forum.server.acceptance;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import junit.framework.Test;
import junit.framework.TestSuite;

//THIS IS THE MAIN CLASS THAT SHOULD RUN ALL THE TEST.
//IMPORTANT NOTICE!!!!!
//THE SERVER MUST RUN IN ORDER TO RUN ALL THOSE TESTS!!!
/**
 * The main test class that will run all the tests, once the suite() method will run
 */
public class MainTesting {

	/**
	 * The main method that will run the tests
	 * @return
	 */
	public static Test suite(){
		TestSuite suite= new TestSuite("Forum Testing");
		//creating a way to distinguish whether there exists a real bridge or not
		BufferedReader in=null;
		String line=null;
		try {
			//the config.ini file contains the information about the existence of
			//the implementation.
			in = new BufferedReader(new FileReader("config.ini"));
		} catch (Exception e) {
			System.out.println("file config.ini not found");
		}
		try {
			//the first line in the file contains the info about the existence of a real bridge
			line = in.readLine();
		} catch (IOException e) {
			System.out.println("problem accessing config.ini");
		}
		//closing the file after reading
		try {
			in.close();
		} catch (IOException e) {
			System.out.println("couldn't close the file");
		}

		ForumBridge tBridge= null;
		//if the read line has the word "true" therefore the real bridge exists. False otherwise.
		if (line.contains("true")){
			//if a real bridge exists, we can instantiate it.
			tBridge = new ProxyBridge(new RealBridge());
		}
		else{
			//using only the proxy bridge...
			tBridge = new ProxyBridge(null);
		}
		//setting the library bridge to be the created above bridge
		TestForumProject.setBridge(tBridge);
		//adding the "donate item test" class to the test suit
		suite.addTest( new TestSuite(LoginTestStory.class));
		suite.addTest( new TestSuite(RegisterTestStory.class));
		return suite;
		}


}
