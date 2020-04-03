package com.example.chatbincollection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class ForgotPassword extends AppCompatActivity {


    EditText email;
    Button sendemail;
    TextView bcklogin;

    FirebaseAuth mAuth;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        email = (EditText)findViewById(R.id.forgotPassEmail);
        sendemail = (Button)findViewById(R.id.sendemailbtn);
        bcklogin = (TextView)findViewById(R.id.textviewbcklogin);
        mAuth = FirebaseAuth.getInstance();

        bcklogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(ForgotPassword.this,Login.class));

            }
        });

        sendemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                sendEmail();
            }
        });


    }

    public void sendEmail()
    {
        String userEmail = email.getText().toString();

        if (userEmail.isEmpty()) {
            email.setError(getString(R.string.input_error_email));
            email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            email.setError(getString(R.string.input_error_email_invalid));
            email.requestFocus();

            return;
        }

        mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(ForgotPassword.this,"Please Check Your Email",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(ForgotPassword.this,Login.class));
                        finishAffinity();
                        email.setText("");
                    }
                    else
                    {
                        String message = task.getException().getMessage();
                        Toast.makeText(ForgotPassword.this,"Error Occured...."+message,Toast.LENGTH_LONG).show();
                    }

                }
            });
        }

    }



