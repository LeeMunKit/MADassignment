package my.edu.tarc.madassignment;

import org.json.JSONArray;

/**
 * Created by User on 3/1/2018.
 */

public class loginPage {
    private String email;
    private String Password;
    private String type;

    public loginPage(){

    }

    public loginPage(String email, String Password, String type) {
        this.email = email;
        this.Password = Password;
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString(){
        return "login details :- /n"+
                "Email : "+email+"/n"+
                "Password : "+Password+"/n"+
                "type : "+type+"/n";
    }
}
