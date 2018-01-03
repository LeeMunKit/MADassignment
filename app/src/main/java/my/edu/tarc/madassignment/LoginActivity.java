package my.edu.tarc.madassignment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;



public class LoginActivity extends AppCompatActivity {
    ConnectivityManager connMgr;
    NetworkInfo networkInfo;
    Boolean isConnected;

    private EditText editTextUs, editTextPassword;
    private Intent intent;
    public static List<loginPage> custList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();
        isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();
        custList = new ArrayList<>();

        editTextUs = (EditText) findViewById(R.id.emailtxt1);
        editTextPassword = (EditText) findViewById(R.id.passwordtxt1);
        read();


    }

    public void read() {
        try {
            // Check availability of network connection.
            if (isConnected) {
                makeServiceCall(getApplicationContext(), getResources().getString(R.string.login_url));
            } else {
                Toast.makeText(getApplicationContext(), "Network is NOT available",
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

                                loginPage cust = new loginPage(email,password);
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

        if(isConnected) {
            String email, password;
            email = String.valueOf(editTextUs.getText());
            password = String.valueOf(editTextPassword.getText());
            //Check record in database
            for (int i = 0; i < custList.size(); i++) {
                if (custList.get(i).getEmail().equals(email) && custList.get(i).getPassword().equals(password)) {
                    valid = true;
                }
            }

            if (valid) {
                Toast.makeText(getApplicationContext(), "Log In Successfully", Toast.LENGTH_SHORT).show();

                intent = new Intent(this, studentMenuActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Invalid Username or Password!", Toast.LENGTH_SHORT).show();

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