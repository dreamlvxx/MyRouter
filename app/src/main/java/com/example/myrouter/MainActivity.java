package com.example.myrouter;

import android.Manifest;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myrouter.utils.XLog;
import com.example.processxx.Greet;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;

import java.util.List;


@Greet("sssss")
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "XXX";

    public TextView sss;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sss = findViewById(R.id.tv_heelo);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        showVirtualNavigationAndStatusBar();
        findViewById(R.id.tv_heelo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               requestPermission();
                hideVirtualNavigationAndStatusBar();
//               showNavigationBar();
            }
        });

        findViewById(R.id.tv_get).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                File files = new File(DEFAULT_LOGOUT_PATH);
//                File[] no = files.listFiles();
//                if(null != no && no.length > 0){
//                    File one = no[0];
//                    String pa = one.getAbsolutePath();
//                    Log.e(TAG, "onClick: " + pa);
//                    System.out.println(read(pa));
//                new AnimationUtil()
//                        .target(sss)
//                        .translationX(0, dip2px(MainActivity.this,100f))
//                        .scaleX(1f,1.53f)
//                        .scaleY(1f,1.53f)
//                        .start(300);
//                }
                showVirtualNavigationAndStatusBar();
//               hideNavigationBar();
            }
        });

    }
    private void setSystemUIVisible(boolean show) {
        if (show) {
            int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            uiFlags |= 0x00001000;
            getWindow().getDecorView().setSystemUiVisibility(uiFlags);
        } else {
            int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            uiFlags |= 0x00001000;
            getWindow().getDecorView().setSystemUiVisibility(uiFlags);
        }
    }

    public static int getScreenWidth(Context context) {
        if(context == null) {
            return -1;
        }
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return context.getResources().getDisplayMetrics().widthPixels;
        }
        Point point = new Point();
        wm.getDefaultDisplay().getRealSize(point);
        return point.x;
    }

    public static int dip2px(Context context, float dipValue) {

        if(context == null) {
            return -1;
        }
        try {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dipValue * scale + 0.5f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (int) dipValue;
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
                        XLog.init(true);
                        XLog.d("这是不待tag的");
                        XLog.d("hehehe", "这是带tag的");
                        XLog.d("这是不待tag的");
                        XLog.d("hehehe", "这是带tag的");
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

    private void hideVirtualNavigationAndStatusBar() {
        View decorView =getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    /**
     * 显示虚拟底部和导航栏
     */
    private void showVirtualNavigationAndStatusBar(){
        View decorView = getWindow().getDecorView();
        decorView .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    private void hideNavigationBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void showNavigationBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE ;
        decorView.setSystemUiVisibility(uiOptions);
    }

}
