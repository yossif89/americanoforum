/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package forum.server.domainlayer;

import forum.tcpcommunicationlayer.ServerResponse;
import forum.tcpcommunicationlayer.ServerSearchResponse;
import java.util.Set;
import java.util.logging.Level;
import org.compass.core.CompassHit;



public class ForumFacadeImpl implements ForumFacade{
    static ForumFacade _instance = null;

    public Forum _facadeForum;
       PersistenceDataHandler _pipe;

    public static ForumFacade getInstance(){
        if (_instance == null){
            _instance = new ForumFacadeImpl();
            return _instance;
        }
        return _instance;

    }

    public ForumFacadeImpl() {
             _pipe = new PersistenceDataHandlerDBImpl();
             _facadeForum = _pipe.getForumFromXml();
              User u = _facadeForum.getRegisteredUsers().get("sepetnit");
              if (u==null){
                  _facadeForum.addVitaly();
              }
               u = _facadeForum.getRegisteredUsers().get("dahany");
              if (u==null){
                  _facadeForum.addYakir();
              }

    }

    public ServerResponse addMessage(String aSubj,String aCont,String us) {
        User u = _facadeForum.getRegisteredUsers().get(us);
        if (u==null){
            Forum.logger.log(Level.INFO,"FacadeForum.addmessage: couldn't find user: "+ us);
            u= new User();
        }
        ServerResponse toRet = new ServerResponse();
        try{
       _facadeForum.addMessage(aSubj, aCont, u);
        }
        catch(Exception e){
            toRet.setEx(e);
            return toRet;
        }
       toRet.setResponse("ok");
       return toRet;
    }

    public ServerResponse deleteMessage(long id,String us) {
        System.out.println("in delete");
         User u = _facadeForum.getRegisteredUsers().get(us);
        if (u==null)
            u= new User();
      ServerResponse toRet = new ServerResponse();
        try{
       _facadeForum.deleteMessage(_facadeForum.getAllMessages().get(new Long(id)), u);
        }
        catch(Exception e){
           e.printStackTrace();
            toRet.setEx(e);
            return toRet;
        }
       toRet.setResponse("ok");
       return toRet;
    }

    public ServerResponse login(String user,String pass) {
          ServerResponse toRet = new ServerResponse();
        try{
         User a = _facadeForum.login(user, pass);
        }
        catch(Exception e){
           
            toRet.setEx(e);
            return toRet;
        }
          catch(IllegalAccessError e){

            return toRet;
        }
       toRet.setResponse(user);
       return toRet;
    }

    public ServerResponse logoff(String us) {
         ServerResponse toRet = new ServerResponse();
         User u = _facadeForum.getOnlineUsers().get(us);
        if (u==null){
            Forum.logger.log(Level.INFO,"FacadeForum.logoff: couldn't find the logged in user");
           toRet.setEx(new IllegalAccessException());
            return toRet;
        }
        try{
          _facadeForum.logoff(u);
        }
        catch(Exception e){
            toRet.setEx(e);
            return toRet;
        }
       toRet.setResponse("ok");
       return toRet;
    }

    public ServerResponse modifyMessage(long messageId,String cont,String us) {
         User u = _facadeForum.getRegisteredUsers().get(us);
        if (u==null)
            u= new User();
      ServerResponse toRet = new ServerResponse();
        try{
          _facadeForum.modifyMessage(_facadeForum.getAllMessages().get(new Long(messageId)), cont, u);
        }
        catch(Exception e){
            System.out.print("99999999999999999999999999999999:");
            e.printStackTrace();
            toRet.setEx(e);
            return toRet;
        }
       toRet.setResponse("ok");
       return toRet;
    }

    public ServerResponse promoteMessage(String us , String username) {
         User u = _facadeForum.getRegisteredUsers().get(us);
        if (u==null)
            u= new User();
       ServerResponse toRet = new ServerResponse();
        try{
          _facadeForum.changeToModerator(u,_facadeForum.getRegisteredUsers().get(username));
        }
        catch(Exception e){
            toRet.setEx(e);
            return toRet;
        }
       toRet.setResponse("ok");
       return toRet;
    }

    public ServerResponse register(String username,String password,String first,String last,String email , String address,String gender) {
         User u = _facadeForum.getRegisteredUsers().get(username);
          ServerResponse toRet = new ServerResponse();
        if (u==null){
            Forum.logger.log(Level.INFO,"ForumFacade: didnt find the user.");
            u= new User();
        }
        else{
            Forum.logger.log(Level.SEVERE,"ForumFacade:couldn't register,user exists.");
            toRet.setEx(new IllegalArgumentException());
            return toRet;
        }
       
        try{
          _facadeForum.register(u, username, password, email, first, last, address, gender);
        }
        catch(Exception e){
            toRet.setEx(e);
            return toRet;
        }
       toRet.setResponse(username);
       return toRet;
    }

    public ServerResponse reply(String subj,String cont,String us , long id) {
         User u = _facadeForum.getRegisteredUsers().get(us);
        if (u==null)
            u= new User();
        ServerResponse toRet = new ServerResponse();
        try{
          _facadeForum.addReply(subj, cont, u, _facadeForum.getAllMessages().get(id));
        }
        catch(Exception e){
            toRet.setEx(e);
            return toRet;
        }
       toRet.setResponse("ok");
       return toRet;
    }

    public ServerResponse viewForum() {
      ServerResponse toRet = new ServerResponse();
    _facadeForum = _pipe.getForumFromXml();

       toRet.setResponse(_facadeForum.toString());
       return toRet;
    }

    public String encode_msgs(CompassHit[] msgs){
        String ans = "";
        for(int i=0; i<msgs.length; i++){
            if (msgs[i].getData() instanceof Message){
                System.out.println("its a message!!!!!!!!!");
            }
            System.out.println("its not a message!!!!!!!!!");
            String subj="";
            String cont="";
            System.out.println(((Message)(msgs[i].getData())).getContent());
            if (((Message)(msgs[i].getData())).getSubject()==null){
                System.out.println("1");
                    subj="EMPTY SUBJECT";
            }
            else{
                    System.out.println("sub if eifffff");
                    subj = ((Message)(msgs[i].getData())).getSubject();
                   System.out.println("there is a subject--->" +subj);
            }

            if (((Message)(msgs[i].getData())).getContent()==null)
                    cont="EMPTY CONTENT";
            else
                    cont = ((Message)(msgs[i].getData())).getContent();

            System.out.println("finish check subj and content");
            ans = ans + ((Message)(msgs[i].getData())).getMsg_id() + "$$" + subj + "$$" + cont + "$$" + ((Message)(msgs[i].getData())).getUsername() + "\n";
        }
        return ans;
    }

    public ServerResponse searchByAuthor(String username, int from, int to) {
        // User u = _facadeForum.getRegisteredUsers().get(us);
        //if (u==null)
        //    u= new User();
        ServerResponse toRet=new ServerResponse();
        try{
          CompassHit[] results = _facadeForum.searchByAuthor(username, from, to);
          Forum.logger.info("after detach "+results.length);
          
          //toRet = new ServerSearchResponse(results);
           String ans="";

        //   for (int i=0; i<results.length; i++){
         //      ans = ans + results[i].toString() + "\n";
          // }
           //System.out.println("ans = "+ans);
           System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
           ans = encode_msgs(results);
           System.out.println("&&&&&&&&&&" + ans);
           toRet.setResponse(ans);

        }
        catch(Exception e){
            toRet.setEx(e);
            return toRet;
        }
       //toRet.setResponse("ok");
       return toRet;
    }

    public ServerResponse searchByContent(String toSearch, int from, int to) {
        ServerResponse toRet=new ServerResponse();
        try{
           // System.out.println("in search by content");
          CompassHit[] results = _facadeForum.searchByContent(toSearch,from,to);
          //toRet = new ServerSearchResponse(results);
           String ans="";

         /*  for (int i=0; i<results.length; i++){
               ans = ans + results[i].toString() + "\n";
           }*/
           //System.out.println("ans = "+ans);
         //  System.out.println("************ RESULTS **************");
           ans = encode_msgs(results);
           //System.out.println(ans);
           toRet.setResponse(ans);

        }
        catch(Exception e){
            toRet.setEx(e);
            return toRet;
        }
       //toRet.setResponse("ok");
       return toRet;
    }

    public ServerResponse encode_allUsers(){
        ServerResponse toRet = new ServerResponse();
        String ans = "all_users:";
        try{
            Set<String> all_users = _facadeForum.getRegisteredUsers().keySet();
            for(String username: all_users){
                ans+= username + "$$";
            }
        } catch (Exception e){
            toRet.setEx(e);
            return toRet;
        }
        toRet.setResponse(ans);
        return toRet;
        
    }
}
