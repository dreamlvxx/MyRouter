package com.example.myrouter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.processxx.Greet;

@Greet("sssss")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
