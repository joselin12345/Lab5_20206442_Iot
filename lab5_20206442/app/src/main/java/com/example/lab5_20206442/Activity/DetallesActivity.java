package com.example.lab5_20206442.Activity;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab5_20206442.R;

import java.util.List;

public class DetallesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.segunda_vista);

        // OBTENCIÓN DEL INTENT:
        Intent intent = getIntent();
        String codigo = intent.getStringExtra("codigo");




    }

}
