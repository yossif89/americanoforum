/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package forum.server.persistencelayer;

/**
 *
 * @author Yossi
 */
public class UserDB {

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String gender;
    private String permission;

    public UserDB(){
        
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getGender() {
        return gender;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getPermission() {
        return permission;
    }

    public String getUsername() {
        return username;
    }
}
