package forum.server.domainlayer;

import forum.tcpcommunicationlayer.ServerResponse;

/**
 * With this interface the Controller layer of the server communicates with the
 * domain layer of the server.  
 * 
 * @author 
 */
public interface ForumFacade {
	
               ServerResponse viewForum();
               ServerResponse addMessage(String aSubj,String aCont,String u);
               ServerResponse login(String user,String pass);
               ServerResponse logoff(String u);
               ServerResponse register(String user,String username,String password,String email,String first,String last , String address,String gender);
               ServerResponse deleteMessage(int id,String u);
               ServerResponse promoteMessage(String u, String a);
               ServerResponse modifyMessage(int messageId,String cont,String u) ;
               ServerResponse reply(String subj,String cont,String u , int id);
               ServerResponse searchByAuthor(String username, int from, int to);
               ServerResponse searchByContent(String toSearch, int from, int to);
	
}
