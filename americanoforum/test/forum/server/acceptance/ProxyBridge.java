package forum.server.acceptance;

/**
 * The proxy bridge that is used to simulate an implementation for the tests in case that
 * the implementation doesn't yet exists.
 */
public class ProxyBridge implements ForumBridge{

	//will get either null value , in case no real bridge exists , or
	//will get the real bridge in order to really test the program.
	public ForumBridge _bridge;

	public ProxyBridge(ForumBridge bridge) {
		this._bridge = bridge;
	}

	

	@Override
	public boolean login(String username , String password) {
            if (_bridge==null)
                   return (username.equals("iluxa13")) && (password.equals("abcd"));

            return _bridge.login(username, password);

	}

	@Override
	public boolean register( String username, String password, String first, String last, String email, String address, String gender){
		  if (_bridge==null){
                      return ((username.equals("felberba"))&&(password.equals("abcde")));
                  }
                  return _bridge.register(username, password, first, last, email, address, gender);
	}

}
