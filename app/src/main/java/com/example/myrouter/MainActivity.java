package com.example.myrouter;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myrouter.utils.Xlog;
import com.example.processxx.Greet;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;

import java.io.File;
import java.util.List;

import static com.example.myrouter.utils.DiskIO.DEFAULT_LOGOUT_PATH;
import static com.example.myrouter.utils.DiskIO.read;

@Greet("sssss")
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "XXX";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_heelo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
            }
        });

        findViewById(R.id.tv_get).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File files = new File(DEFAULT_LOGOUT_PATH);
                File[] no = files.listFiles();
                if(null != no && no.length > 0){
                    File one = no[0];
                    String pa = one.getAbsolutePath();
                    Log.e(TAG, "onClick: " + pa);
                    System.out.println(read(pa));
                }
            }
        });

    }

    private void requestPermission() {
        AndPermission.with(this)
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .rationale(new Rationale() {
                    @Override
                    public void showRationale(Context context, List<String> permissions, RequestExecutor executor) {
                        executor.execute();
                    }
                })
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        Log.d(TAG, "onAction: success");
                        Xlog.init(true);
                        Xlog.d("这是不待tag的");
                        Xlog.d("hehehe", "这是带tag的");
                        Xlog.d("这是不待tag的");
                        Xlog.d("hehehe", "这是带tag的");
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        if (AndPermission.hasAlwaysDeniedPermission(MainActivity.this, permissions)) {
                            AndPermission.permissionSetting(MainActivity.this).execute();
                            return;
                        }
                    }
                })
                .start();
    }


}
