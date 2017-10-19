package com.example.moham.nearby;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {
    EditText username;
    EditText password;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        username = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);
        button = (Button) findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences userDetails = SignUp.this.getSharedPreferences("userdetails", MODE_PRIVATE);
                SharedPreferences.Editor editor = userDetails.edit();
                editor.putString("username", username.getText().toString());
                editor.putString("password", password.getText().toString());
                editor.putString("logged", "logged");
                editor.apply();

                Intent intent = new Intent(SignUp.this, SignIn.class);
                Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_LONG).show();

                startActivity(intent);

            }
        });


    }


}