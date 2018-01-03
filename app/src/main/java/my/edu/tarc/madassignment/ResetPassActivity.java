package my.edu.tarc.madassignment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ResetPassActivity extends AppCompatActivity {
    EditText editTextPass, editTextEmail;
    Button reset;
    String Password, Email;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);

        editTextEmail = (EditText) findViewById(R.id.resetemail);
        editTextPass = (EditText) findViewById(R.id.resetpass);

        reset = (Button) findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Password = editTextPass.getText().toString();
                if (Password.isEmpty()) {
                    editTextPass.setError("Please enter Password");
                    return;
                }
                Email = editTextEmail.getText().toString();
                if (Email.isEmpty()) {
                    editTextEmail.setError("Please enter Email");
                    return;
                }
                Background b = new Background();
                b.execute(Password, Email);
            }
        });
    }

    class Background extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            String password = params[0];
            String email = params[1];
            String data = "";
            int tmp;

            try {
                URL url = new URL("https://tarucclassroom.000webhostapp.com/reset_password.php");
                String urlparams = "password=" + password + "&email=" + email;

                HttpURLConnection hurl = (HttpURLConnection) url.openConnection();
                hurl.setDoOutput(true);
                OutputStream os = hurl.getOutputStream();
                os.write(urlparams.getBytes());
                os.flush();
                os.close();

                InputStream is = hurl.getInputStream();
                while ((tmp = is.read()) != -1) {
                    data += (char) tmp;
                }
                is.close();
                hurl.disconnect();
                return data;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if (s.equals("")) {
                s = "Data not updated";
            } else {
                Toast.makeText(ResetPassActivity.this, "Data Updated Successfully !", Toast.LENGTH_SHORT).show();

            }
        }
    }
    public void loginbtn (View v){
        Intent intent1 = new Intent(this, MainActivity.class);
        startActivity(intent1);
    }
}