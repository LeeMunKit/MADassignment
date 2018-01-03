/**
 * Created by User on 21-Dec-17.
 */

public class register {
    private String name;
    private String email;
    private String pass;

    public register(){

    }

    public register(String name, String email, String pass){
        this.name = name;
        this.email = email;
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String toString(){
        return "register{"+ "name='" + name +'\'' + ", email='" + email +'\''+ ", pass ='" + pass + '\''+'}';
    }

}
