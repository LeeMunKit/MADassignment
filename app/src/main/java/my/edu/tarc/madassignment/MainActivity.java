package my.edu.tarc.madassignment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getActionBar().setTitle("Homepage");
    }

    public void lecbtn (View v){
        Intent intent = new Intent(this, lecLoginActivity.class);
        startActivity(intent);
    }

    public void studbtn(View view){
        Intent intent1 = new Intent(this, LoginActivity.class);
        startActivity(intent1);
    }


}
