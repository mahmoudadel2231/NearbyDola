package com.example.moham.nearby;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignIn extends AppCompatActivity {
    EditText username;
    EditText password;
    Button signin;
    TextView signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        username = (EditText) findViewById(R.id.editText3);
        password = (EditText) findViewById(R.id.editText5);
        signin = (Button) findViewById(R.id.button2);
        signup = (TextView) findViewById(R.id.textView5);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences userDetails = SignIn.this.getSharedPreferences("userdetails", MODE_PRIVATE);
                String Uname = userDetails.getString("username", "mn");
                String pass = userDetails.getString("password", "mm");
                Intent intent = new Intent(SignIn.this, MainActivity.class);
                if (Uname.equals(username.getText().toString().trim()) && pass.equals(password.getText().toString().trim())) {
                    intent.putExtra("us", username.getText().toString());
                    intent.putExtra("pa", password.getText().toString());
                    startActivity(intent);
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(SignIn.this, SignUp.class);
                startActivity(intent2);
            }
        });
    }
}
