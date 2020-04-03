package com.example.chatbincollection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {



    public static int Splash_time_out = 3000;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                SharedPreferences preferences =getSharedPreferences("loginref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                if (preferences.getBoolean("saveLogin",false)){
                    Intent intent=new Intent(MainActivity.this,HomePage.class);
                    startActivity(intent);
                }
                else {
                    Intent loginp = new Intent(MainActivity.this, Login.class);
                    startActivity(loginp);
                    finishAffinity();
                }
                /*else{
                    Log.d("Email",email);
                    Log.d("Password",password);
                    Intent intent = new Intent(SplashScreen.this,HomePage.class);
                    startActivity(intent);
                    finishAffinity();
                }*/
            }
        }, Splash_time_out);



    }
}
