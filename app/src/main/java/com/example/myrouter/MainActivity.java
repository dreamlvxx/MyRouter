package com.example.myrouter;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myrouter.utils.Xlog;
import com.example.processxx.Greet;

@Greet("sssss")
public class MainActivity extends AppCompatActivity {
    private static final String TAG ="XXX";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_heelo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Xlog().addInterceptor(new Xlog.DefaultInterceptor()).addInterceptor(new Xlog.DiskInterceptor())
                        .log();
            }
        });

    }


}
