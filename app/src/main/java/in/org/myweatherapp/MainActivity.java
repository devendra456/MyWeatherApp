package in.org.myweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText userId,password;
    private Button loginButton;
    private String user="admin";
    private String pass="1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userId=findViewById(R.id.user_id);
        password=findViewById(R.id.password);
        loginButton=findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        if(!TextUtils.isEmpty(userId.getText())){
            if(!TextUtils.isEmpty(password.getText())){
                if(userId.getText().toString().equals(user) && password.getText().toString().equals(pass)){
                    startActivity(new Intent(this,WeatherActivity.class));
                    finish();
                }
                else {
                    Toast.makeText(this, "Incorrect user Id or password", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Please enter user Id", Toast.LENGTH_SHORT).show();
        }
    }
}