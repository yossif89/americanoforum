package forum.server.domainlayer;

/**
 * This class holds the private details of a registered user
 * @author Ilya
 */
public class Details {
    
	private String _username;
        private String _password;
	private String _email;
	private String _first_name;
	private String _last_name;
	private String _address;
	private String _gender;

    public Details(String _username, String _password, String _email, String _first_name, String _last_name, String _address, String _gender) {
        this._username = _username;
        this._password = _password;
        this._email = _email;
        this._first_name = _first_name;
        this._last_name = _last_name;
        this._address = _address;
        this._gender = _gender;
    }

    public void setAddress(String _address) {
        this._address = _address;
    }

    public void setEmail(String _email) {
        this._email = _email;
    }

    public void setFirst_name(String _first_name) {
        this._first_name = _first_name;
    }

    public void setGender(String _gender) {
        this._gender = _gender;
    }

    public void setLast_name(String _last_name) {
        this._last_name = _last_name;
    }

    public void setPassword(String _password) {
        this._password = _password;
    }

    public void setUsername(String _username) {
        this._username = _username;
    }

    public String getAddress() {
        return _address;
    }

    public String getEmail() {
        return _email;
    }

    public String getFirst_name() {
        return _first_name;
    }

    public String getGender() {
        return _gender;
    }

    public String getLast_name() {
        return _last_name;
    }

    public String getPassword() {
        return _password;
    }

    public String getUsername() {
        return _username;
    }
	
}