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
                Xlog.init(true);
                Xlog.d("这是不待tag的");
                Xlog.d("hehehe","这是带tag的");
                Xlog.d("这是不待tag的");
                Xlog.d("hehehe","这是带tag的");
            }
        });

    }


}
