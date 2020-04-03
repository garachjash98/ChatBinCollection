package com.example.chatbincollection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    EditText username,emailid,passwd,cpasswd;
    TextView signin;
    Button signupbtn;
    ProgressDialog dialog;
    DatabaseReference db;

    private FirebaseAuth mAuth;


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        username = (EditText)findViewById(R.id.nameedittext);
        emailid = (EditText)findViewById(R.id.emailedittext);
        passwd = (EditText)findViewById(R.id.passwordedittext);
        cpasswd = (EditText)findViewById(R.id.confirmpasswordedittext);
        signin = (TextView)findViewById(R.id.signintextview);
        signupbtn = (Button)findViewById(R.id.signupbutton);
        mAuth = FirebaseAuth.getInstance();



        dialog = new ProgressDialog(SignUp.this);

        dialog.setMessage("Registering User...");
        dialog.dismiss();

        db= FirebaseDatabase.getInstance().getReference("Users");
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(SignUp.this,Login.class));
            }
        });


        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                signupuser();


            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            //handle the already login user
        }
    }
    public void signupuser()
    {
        final String name = username.getText().toString().trim();
        final String email = emailid.getText().toString().trim();
        String password = passwd.getText().toString().trim();
        String cpassword = cpasswd.getText().toString().trim();



        if (name.isEmpty()) {
            username.setError(getString(R.string.input_error_name));
            username.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            emailid.setError(getString(R.string.input_error_email));
            emailid.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailid.setError(getString(R.string.input_error_email_invalid));
            emailid.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            passwd.setError(getString(R.string.input_error_password));
            passwd.requestFocus();
            return;
        }

        if (password.length() < 6) {
            passwd.setError(getString(R.string.input_error_password_length));
            passwd.requestFocus();
            return;
        }

        if(!password.equals(cpassword))
        {
            cpasswd.setError(getString(R.string.input_error_comfirmpassword_invalid));
            cpasswd.requestFocus();
            return;
        }
        dialog.show();

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {

                        if(task.isSuccessful())
                        {



                            dialog.dismiss();

                            User user = new User(name,email);

                            db.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {

                                    if (task.isSuccessful()) {

                                        mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task)
                                            {
                                                Toast.makeText(SignUp.this, getString(R.string.registration_success), Toast.LENGTH_LONG).show();
                                                username.setText("");
                                                emailid.setText("");
                                                passwd.setText("");
                                                cpasswd.setText("");

                                            }
                                        });

                                    } else {

                                        Toast.makeText(SignUp.this,"Unsuccessfull",Toast.LENGTH_LONG).show();
                                    }

                                }
                            });



                        }
                        else
                        {
                            dialog.dismiss();
                            Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            username.setText("");
                            emailid.setText("");
                            passwd.setText("");
                            cpasswd.setText("");

                        }

                    }
                });


    }
}
