package com.example.moham.nearby;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class SignUp extends AppCompatActivity {
    private static final int PICK_IMAGE = 100;
    EditText username;
    EditText password;
    Button button;
    TextView textView;
    EditText Birthday;
    ImageView imageView;
    private Calendar calendar;
    private int year, month, day;
    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2 + 1, arg3);
                }
            };

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        textView = (TextView) findViewById(R.id.SignIn);
        username = (EditText) findViewById(R.id.EMAIL_e);
        Birthday = (EditText) findViewById(R.id.BIRTHDAY_e);
        password = (EditText) findViewById(R.id.PASSWEORD_e);
        button = (Button) findViewById(R.id.SignUp);
        imageView = (ImageView) findViewById(R.id.imageView3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().trim().endsWith("@gmail.com") || username.getText().toString().trim().endsWith("@yahoo.com") || username.getText().toString().trim().endsWith("@outlook.com")) {
                    SharedPreferences userDetails = SignUp.this.getSharedPreferences("userdetails", MODE_PRIVATE);
                    SharedPreferences.Editor editor = userDetails.edit();
                    editor.putString("username", username.getText().toString());
                    editor.putString("password", password.getText().toString());
                    editor.putString("logged", "logged");
                    editor.apply();
                    Intent intent = new Intent(SignUp.this, SignIn.class);
                    Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_LONG).show();
                    startActivity(intent);

                } else {
                    Toast.makeText(SignUp.this, "Worng Email !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, SignIn.class);
                startActivity(intent);
            }
        });
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        // showDate(year, month+1, day);
        Birthday.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                setDate(view);
                return false;
            }
        });
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private void showDate(int year, int month, int day) {
        Birthday.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    public void openGallery(View v) {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }
}