package my.edu.tarc.madassignment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ConnectivityManager connMgr;
    NetworkInfo networkInfo;
    Boolean isConnected;

    private EditText editTextUs, editTextPassword;
    private RadioButton teachercheckbox, studentcheckbox;
    private Intent intent;
    public static List<loginPage> custList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getActionBar().setTitle("Homepage");
        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();
        isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();
        custList = new ArrayList<>();

        editTextUs = (EditText) findViewById(R.id.emailtxt1);
        editTextPassword = (EditText) findViewById(R.id.passwordtxt1);
        teachercheckbox = (RadioButton) findViewById(R.id.tchradioButton);
        studentcheckbox = (RadioButton) findViewById(R.id.stdradioButton);

        read();
    }

    public void read() {
        try {
            // Check availability of network connection.
            if (isConnected) {
                makeServiceCall(getApplicationContext(), getResources().getString(R.string.login_url));
            } else {
                Toast.makeText(getApplicationContext(), "Network is Not available",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "Error reading record:" + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    // Triggers when LOGIN Button clicked
    public void makeServiceCall(Context context, String url) {

        RequestQueue queue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            //Clear list
                            custList.clear();

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject recordResponse = (JSONObject) response.get(i);
                                String email = recordResponse.getString("Email");
                                String password = recordResponse.getString("Password");
                                String type = recordResponse.getString("Type");

                                loginPage cust = new loginPage(email,password,type);
                                custList.add(cust);
                            }

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error 1:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), "Error 2:" + volleyError.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
        queue.add(jsonObjectRequest);

    }
    public void login1(View v) {
        boolean valid = false;
        boolean validtype = false;

        if(isConnected) {
            String email, password;
            String type = new String();
            int index = -1;

            email = String.valueOf(editTextUs.getText());
            if (email.isEmpty()) {
                editTextUs.setError("Please enter email");
                return;
            }
            password = String.valueOf(editTextPassword.getText());
            if (password.isEmpty()) {
                editTextPassword.setError("Please enter password");
                return;
            }

            if (teachercheckbox.isChecked()) {
                type = String.valueOf(teachercheckbox.getText());

            }else {
                type = String.valueOf(studentcheckbox.getText());
            }

            //Check record in database
            for (int i = 0; i < custList.size(); i++) {
                if (custList.get(i).getEmail().equals(email) && custList.get(i).getPassword().equals(password)) {
                    valid = true;
                    index = i;
                    break;
                }
            }
                if (valid && custList.get(index).getType().equals(type)) {
                    validtype = true;
                }else {
                    Toast.makeText(getApplicationContext(), "Invalid Username or Password!", Toast.LENGTH_SHORT).show();
                }

                if(validtype && type.equals("Teacher")) {
                    Toast.makeText(getApplicationContext(), "Log In Successfully", Toast.LENGTH_SHORT).show();
                    intent = new Intent(this, teacherMenuActivity.class);
                    startActivity(intent);

                }else if(validtype && type.equals("Student")){
                    Toast.makeText(getApplicationContext(), "Log In Successfully", Toast.LENGTH_SHORT).show();
                    intent = new Intent(this, studentMenuActivity.class);
                    startActivity(intent);

                }
        }else{
                Toast toast = new Toast(getApplicationContext());
                toast.makeText(getApplicationContext(), "No Network Connection.", Toast.LENGTH_SHORT).show();
            }

    }

    public void regbtn (View v){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void resetbtn (View v){
        Intent intent1 = new Intent(this, ResetPassActivity.class);
        startActivity(intent1);
    }

}
