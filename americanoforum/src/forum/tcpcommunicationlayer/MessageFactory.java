/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package forum.tcpcommunicationlayer;

/**
 *
 * @author Ilya
 */
public class MessageFactory {

    public AddMessageMessage CreateAddMessage(String subj,String cont,String user ){
        return new AddMessageMessage(subj, cont, user);
    }

     public ModifyMessageMessage CreateModifyMessage(int id,String cont,String user ){
        return new ModifyMessageMessage(id , cont, user);
    }
     
     public AddReplyMessage CreateReplyMessage(int id,String subj,String cont,String user ){
        return new AddReplyMessage(id , subj, cont, user);
    }
     
     public DeleteMessageMessage CreateDeleteMessage(int id ,String user){
         return new DeleteMessageMessage(id, user);
     }

     public LoginMessage CreateLoginMessage(String user,String password){
         return new LoginMessage(user, password, "");
     }
     
       public LogoffMessage CreateLogoffMessage(String user){
         return new LogoffMessage(user);
       }
       
       public PromoteMessage CreatePromoteMessage(String user1,String user2){
         return new PromoteMessage(user1,user2);
       }

        public RegisterMessage CreateRegisterMessage(String user,String pass,String mail,String first,String last,String address,String gender,String u ){
              return new RegisterMessage(user, pass, mail, first, last, address, gender, u);
          }

               public  SearchByContentMessage  CreateSearchByContentMessage(String toSearch, int from, int to){
         return new SearchByContentMessage(toSearch, from, to);
       }

         public  SearchByAuthorMessage  CreateSearchByAuthorMessage(String toSearch, int from, int to){
         return new SearchByAuthorMessage(toSearch, from, to);
       }

public  SearchMessage  CreateSearchMessage(String toSearch,String u){
        return new SearchMessage(toSearch, u);
}
                      public  ViewForumMessage  CreateViewForumMessage(){
         return new ViewForumMessage();
       }





     





}
