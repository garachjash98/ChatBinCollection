package com.example.chatbincollection;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class HomePage extends AppCompatActivity {


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(HomePage.this)
                .setMessage("Logout ChatBot App:")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        SharedPreferences preferences = getSharedPreferences("loginref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.apply();
                        FirebaseAuth.getInstance().signOut();
                        Intent abc = new Intent(HomePage.this, MainActivity.class);
                        abc.addFlags(FLAG_ACTIVITY_CLEAR_TOP);
                        abc.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(abc);
                        HomePage.this.finish();


                    }
                })
                .setNegativeButton("No", null)
                .setCancelable(false);

        AlertDialog start = builder.create();
        start.show();
    }





}

