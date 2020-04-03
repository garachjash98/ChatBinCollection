package com.example.chatbincollection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    TextView forgotpass,signup;
    EditText email,password;
    Button loginbtn;
    ProgressDialog dialog;
    FirebaseAuth mAuth;

    CheckBox remember;



    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Boolean savelogin;



    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        forgotpass = (TextView)findViewById(R.id.textviewfpass);
        signup = (TextView)findViewById(R.id.textviewsignup);
        email = (EditText)findViewById(R.id.editTextEmail);
        password = (EditText)findViewById(R.id.editTextPassword);
        loginbtn = (Button)findViewById(R.id.cirLoginButton);
        mAuth = FirebaseAuth.getInstance();


        sharedPreferences = getSharedPreferences("loginref", MODE_PRIVATE);
        remember = (CheckBox) findViewById(R.id.savelogincheckbox);
        editor = sharedPreferences.edit();

        savelogin = sharedPreferences.getBoolean("saveLogin", false);
        savelogin=sharedPreferences.getBoolean("saveLogin",true);
        if(savelogin==true) {
            email.setText(sharedPreferences.getString("username", null));
            password.setText(sharedPreferences.getString("password", null));
        }

        dialog = new ProgressDialog(Login.this);

        dialog.setMessage("Logging In...");
        dialog.dismiss();



        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Login.this,ForgotPassword.class));
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Login.this,SignUp.class));
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                loginuser();
            }
        });
    }


    public void loginuser()
    {
        String Email, Password;
        Email = email.getText().toString();
        Password = password.getText().toString();
        if (Email.isEmpty()) {
            email.setError(getString(R.string.input_error_email));
            email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            email.setError(getString(R.string.input_error_email_invalid));
            email.requestFocus();
            dialog.dismiss();
            return;
        }

        if (Password.isEmpty()) {
            password.setError(getString(R.string.input_error_password));
            password.requestFocus();
            dialog.dismiss();
            return;
        }

        dialog.show();
        mAuth.signInWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            if(mAuth.getCurrentUser().isEmailVerified())
                            {
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                                finish();
                                checkbox();
                                Intent intent = new Intent(Login.this, HomePage.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                email.setText("");
                                password.setText("");
                            }
                            else
                            {
                                dialog.dismiss();
                                Toast.makeText(Login.this,"Please Verify your Email Address",Toast.LENGTH_LONG).show();
                                email.setText("");
                                password.setText("");
                            }





                        } else {


                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Login failed! Please try again later", Toast.LENGTH_LONG).show();
                            email.setText("");
                            password.setText("");

                        }
                    }
                });

    }

    public void checkbox() {
        String emailid, passwd;
        emailid = email.getText().toString();
        passwd = email.getText().toString();
        if (remember.isChecked()) {
            editor.putBoolean("saveLogin", true);
            editor.putString("username", emailid);
            editor.putString("password", passwd);
            editor.commit();
        }
        else {
            editor.putBoolean("saveLogin",false);
            editor.apply();
        }
    }
}
