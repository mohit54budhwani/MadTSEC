package com.example.mohityoureamazing.madclubtsec;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserRegistration extends AppCompatActivity implements View.OnClickListener {
    private TextView loginhere;        //TextviewSignup
    private Button regandloginbutton;  //buttonRegister
    private EditText choosepassword;
    private EditText regusername;
    private EditText confirmpassword;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        loginhere = (TextView) findViewById(R.id.loginhere);
        loginhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

        /*if (firebaseAuth.getCurrentUser() != null){
            //user is already logged in
            //directly start profile activity
            finish();
            startActivity(new Intent(getApplicationContext(),HomeScreen.class));
        }*/

        regandloginbutton = (Button) findViewById(R.id.registerbutton);
        regandloginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        choosepassword = (EditText) findViewById(R.id.choosepassword);
        regusername = (EditText) findViewById(R.id.regusername);

        confirmpassword=(EditText) findViewById(R.id.confirmpassword);

    }

    private void registerUser(){
        String email = regusername.getText().toString().trim();
        String password = choosepassword.getText().toString().trim();
        String password2 = confirmpassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this,"Please enter e-mail.",Toast.LENGTH_SHORT).show();
            //stopping from executing further
            return;
        }

        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this,"Please enter password.",Toast.LENGTH_SHORT).show();
            //stopping from executing further
            return;
        }
/*
        if(password != password2){
            //password not matching
            Toast.makeText(this,"Password not matching.",Toast.LENGTH_SHORT).show();
            //stopping from executing further
            return;
        }
   */

        //validation is okay   show progressbar

        progressDialog.setMessage("Registering User...");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()){
                        // registration successful and start home screen
                        Toast.makeText(UserRegistration.this,"Registration Successfull",Toast.LENGTH_SHORT).show();
                        //finish();  ************
                        //startActivity(new Intent(getApplicationContext(),HomeScreen.class));
                        //openHomeScreen();
                }
                else{
                    Toast.makeText(UserRegistration.this,"Could not Register",Toast.LENGTH_SHORT).show();
                }
            }


            });
        }

    public void openHomeScreen(){
        Intent intent = new Intent(this,HomeScreen.class);
        startActivity(intent);
    }
    public void openMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }


    @Override
    public void onClick(View view){
        if(view == regandloginbutton){
            registerUser();
        }

    }

}
