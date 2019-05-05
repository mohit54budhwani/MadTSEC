package com.example.mohityoureamazing.madclubtsec;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button loginbutton ;
    private EditText loginusername;
    private EditText loginpassword;
    private TextView registerhere;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog= new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        /*
        if (firebaseAuth.getCurrentUser() != null){
            //user is already logged in
            //directly start profile activity
            //finish();
            startActivity(new Intent(getApplicationContext(),HomeScreen.class));
        }*/

        loginusername=(EditText) findViewById(R.id.loginusername);
        loginpassword = (EditText) findViewById(R.id.loginpassword);


        registerhere = (TextView)findViewById(R.id.registerhere);
        registerhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserRegistration();
            }
        });

        loginbutton = (Button) findViewById(R.id.loginbutton);
        loginbutton.setOnClickListener(this);

    }

    public void openHomeScreen(){
        Intent intent = new Intent(this,HomeScreen.class);
        startActivity(intent);
    }

    public void openUserRegistration(){
        Intent intent = new Intent(this,UserRegistration.class);
        startActivity(intent);
    }

    private void userLogin(){
        String email = loginusername.getText().toString().trim();
        String password =  loginpassword.getText().toString().trim();

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


        //validation is okay   show progressbar

        progressDialog.setMessage("Processing...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()){
                            //start the profile activity
                            //finish();
                            Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(MainActivity.this,HomeScreen.class));
                            //openHomeScreen();
                        }
                        else{
                            Toast.makeText(MainActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view==loginbutton){
            userLogin();
        }

    }


}
