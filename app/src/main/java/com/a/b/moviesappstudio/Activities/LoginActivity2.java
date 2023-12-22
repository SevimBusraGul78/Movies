package com.a.b.moviesappstudio.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.a.b.moviesappstudio.R;

public class LoginActivity2 extends AppCompatActivity {
    private EditText userEdit,passEdt;
    private Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        initView();
    }

    private void initView() {
        userEdit=findViewById(R.id.editTextText);
        passEdt=findViewById(R.id.editTextText2);
        loginBtn=findViewById(R.id.loginbtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userEdit.getText().toString().isEmpty() || passEdt.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity2.this,"Please Fill your user and password",Toast.LENGTH_LONG).show();

                } else if (userEdit.getText().toString().equals("test") && passEdt.getText().toString().equals("test")) {
                     startActivity(new Intent(LoginActivity2.this,MainActivity.class));
                }else {
                    Toast.makeText(LoginActivity2.this,"Your user and password is not correct",Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}