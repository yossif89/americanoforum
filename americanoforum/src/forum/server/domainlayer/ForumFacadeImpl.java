/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package forum.server.domainlayer;

import forum.tcpcommunicationlayer.ServerResponse;
import java.util.logging.Level;



public class ForumFacadeImpl implements ForumFacade{
        public Forum _facadeForum;
       PersistenceDataHandler _pipe;

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
            Forum.logger.log(Level.INFO,"FacadeForum.addmessage: couldn't find user");
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

    public ServerResponse deleteMessage(int id,String us) {
         User u = _facadeForum.getRegisteredUsers().get(us);
        if (u==null)
            u= new User();
      ServerResponse toRet = new ServerResponse();
        try{
       _facadeForum.deleteMessage(_facadeForum.getAllMessages().get(new Integer(id)), u);
        }
        catch(Exception e){
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
         User u = _facadeForum.getRegisteredUsers().get(us);
        if (u==null){
            Forum.logger.log(Level.INFO,"FacadeForum.logoff: couldn't find the logged in user");
            u= new User();
        }
         ServerResponse toRet = new ServerResponse();
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

    public ServerResponse modifyMessage(int messageId,String cont,String us) {
         User u = _facadeForum.getRegisteredUsers().get(us);
        if (u==null)
            u= new User();
      ServerResponse toRet = new ServerResponse();
        try{
          _facadeForum.modifyMessage(_facadeForum.getAllMessages().get(new Integer(messageId)), cont, u);
        }
        catch(Exception e){
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

    public ServerResponse register(String us,String username,String password,String email,String first,String last , String address,String gender) {
         User u = _facadeForum.getRegisteredUsers().get(us);
        if (u==null){
            Forum.logger.log(Level.INFO,"ForumFacade: didnt find the user.");
            u= new User();
        }
        ServerResponse toRet = new ServerResponse();
        try{
          _facadeForum.register(u, username, password, email, first, last, address, gender);
        }
        catch(Exception e){
            toRet.setEx(e);
            return toRet;
        }
       toRet.setResponse("ok");
       return toRet;
    }

    public ServerResponse reply(String subj,String cont,String us , int id) {
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



}
