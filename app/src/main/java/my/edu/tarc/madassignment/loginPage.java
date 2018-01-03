package my.edu.tarc.madassignment;

import org.json.JSONArray;

/**
 * Created by User on 3/1/2018.
 */

public class loginPage {
    private String email;
    private String Password;

    public loginPage(){

    }

    public loginPage(String email, String Password) {
        this.email = email;
        this.Password = Password;
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
        Password = password;
    }

    public String toString(){
        return "login details :- /n"+
                "Email : "+email+"/n"+
                "Password : "+Password+"/n";
    }
}
