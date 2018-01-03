package my.edu.tarc.madassignment;

/**
 * Created by User on 21-Dec-17.
 */

public class register {
    private String name;
    private String email;
    private String pass;

    public register(String name, String email, String pass){
        this.email = email;
        this.pass = pass;
        this.name = name;
    }

    public register(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String toString(){
        return "register{"+ "email='" + email +'\'' + ", pass='" + pass +'\''+ ", name ='" + name+ '\''+'}';
    }
}
