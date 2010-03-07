package forum.server.domainlayer;

public interface UserPermission {

	public void addMessage(String aSbj, String aCont);

	public void modifyMessage(Message aMsg, String aCont);

	public void viewMessage(Message aMsg);

	public void reply(Message aParent_msg, String aSbj, String aCont);

	public UserPermission getInstance();
}