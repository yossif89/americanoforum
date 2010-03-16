package forum.server.domainlayer;

public class Details {
    
	private String _username;
     private String _password;
	private String _email;
	private String _first_name;
	private String _last_name;
	private String _address;
	private String _gender;
/**
 * constractor
 * @param _username - the nickname of the user
 * @param _password - password
 * @param _email - email address
 * @param _first_name
 * @param _last_name
 * @param _address
 * @param _gender - male or female
 */
    public Details(String _username, String _password, String _email, String _first_name, String _last_name, String _address, String _gender) {
        this._username = _username;
        this._password = _password;
        this._email = _email;
        this._first_name = _first_name;
        this._last_name = _last_name;
        this._address = _address;
        this._gender = _gender;
    }
/**
 * sets the address in the details
 * @param _address
 */
    public void setAddress(String _address) {
        this._address = _address;
    }
/**
 * sets the email in the details
 * @param _email
 */
    public void setEmail(String _email) {
        this._email = _email;
    }
/**
 * sets the first name in the details
 * @param _first_name
 */
    public void setFirst_name(String _first_name) {
        this._first_name = _first_name;
    }
/**
 * sets the gender in the details
 * @param _gender - male or female
 */
    public void setGender(String _gender) {
        this._gender = _gender;
    }
/**
 * sets the last name in the details
 * @param _last_name
 */
    public void setLast_name(String _last_name) {
        this._last_name = _last_name;
    }
/**
 * sets the password in the details
 * @param _password
 */
    public void setPassword(String _password) {
        this._password = _password;
    }
/**
 * sets the user name in the details
 * @param _username - nickname of the user
 */
    public void setUsername(String _username) {
        this._username = _username;
    }
/**
 * getter for the address from the details
 * @return address
 */
    public String getAddress() {
        return _address;
    }
/**
 * getter for the email from the details
 * @return the email
 */
    public String getEmail() {
        return _email;
    }
/**
 * getter for the first name from the details
 * @return first name
 */
    public String getFirst_name() {
        return _first_name;
    }
/**
 * getter for the gender from the details
 * @return the gender in the details
 */
    public String getGender() {
        return _gender;
    }
/**
 * getter for the last name from the details
 * @return last name
 */
    public String getLast_name() {
        return _last_name;
    }
/**
 * getter for the password from the details
 * @return password
 */
    public String getPassword() {
        return _password;
    }
/**
 * getter for the username from the details
 * @return user name
 */
    public String getUsername() {
        return _username;
    }
}//class