/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package forum.server.domainlayer;

import forum.tcpcommunicationlayer.ServerResponse;
import forum.tcpcommunicationlayer.ServerSearchResponse;
import java.util.logging.Level;



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
             _pipe = new PersistenceDataHandlerImpl();
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

       toRet.setResponse(_facadeForum.toString());
       return toRet;
    }

    public String encode_msgs(SearchHit[] msgs){
        String ans = "";
        for(int i=0; i<msgs.length; i++){
            String subj="";
            String cont="";
            if (msgs[i].getMessage().getSubject().equals(""))
                    subj="EMPTY SUBJECT";
            else
                    subj = msgs[i].getMessage().getSubject();
            if (msgs[i].getMessage().getContent().equals(""))
                    cont="EMPTY CONTENT";
            else
                    cont = msgs[i].getMessage().getContent();
            ans = ans + msgs[i].getMessage().getMsg_id() + "$$" + subj + "$$" + cont + "$$" + msgs[i].getMessage().getCreator().getDetails().getUsername() + "\n";
        }
        return ans;
    }

    public ServerResponse searchByAuthor(String username, int from, int to) {
        // User u = _facadeForum.getRegisteredUsers().get(us);
        //if (u==null)
        //    u= new User();
        ServerResponse toRet=new ServerResponse();
        try{
          SearchHit[] results = _facadeForum.searchByAuthor(username, from, to);
          //toRet = new ServerSearchResponse(results);
           String ans="";

        //   for (int i=0; i<results.length; i++){
         //      ans = ans + results[i].toString() + "\n";
          // }
           //System.out.println("ans = "+ans);
           ans = encode_msgs(results);
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
          SearchHit[] results = _facadeForum.searchByContent(toSearch,from,to);
          //toRet = new ServerSearchResponse(results);
           String ans="";

           for (int i=0; i<results.length; i++){
               ans = ans + results[i].toString() + "\n";
           }
           System.out.println("ans = "+ans);
           toRet.setResponse(ans);

        }
        catch(Exception e){
            toRet.setEx(e);
            return toRet;
        }
       //toRet.setResponse("ok");
       return toRet;
    }

}
