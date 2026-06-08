package com.example.autoclicker;

import android.accessibilityservice.AccessibilityService;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    
    private Spinner frequencySpinner;
    private Spinner durationSpinner;
    private Button startButton;
    private Button stopButton;
    private TextView statusText;
    
    private Handler handler = new Handler(Looper.getMainLooper());
    private boolean isRunning = false;
    private long startTime;
    private long duration;
    private int clickInterval;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // 初始化UI组件
        frequencySpinner = findViewById(R.id.frequency_spinner);
        durationSpinner = findViewById(R.id.duration_spinner);
        startButton = findViewById(R.id.start_button);
        stopButton = findViewById(R.id.stop_button);
        statusText = findViewById(R.id.status_text);
        
        // 设置频率选项
        String[] frequencies = {"1秒1次", "1秒2次"};
        ArrayAdapter<String> frequencyAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, frequencies);
        frequencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        frequencySpinner.setAdapter(frequencyAdapter);
        
        // 设置持续时间选项
        String[] durations = {"5秒", "10秒", "15秒", "20秒", "25秒", "30秒", "60秒"};
        ArrayAdapter<String> durationAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, durations);
        durationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        durationSpinner.setAdapter(durationAdapter);
        
        // 检查无障碍服务是否启用
        checkAccessibilityService();
        
        // 设置按钮监听器
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAutoClick();
            }
        });
        
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAutoClick();
            }
        });
    }
    
    private void checkAccessibilityService() {
        int accessibilityEnabled = 0;
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                    getContentResolver(),
                    Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        
        if (accessibilityEnabled == 0) {
            // 无障碍服务未启用，提示用户启用
            statusText.setText("请先启用无障碍服务");
            Toast.makeText(this, "请先启用无障碍服务", Toast.LENGTH_LONG).show();
            
            // 打开无障碍设置页面
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
        } else {
            statusText.setText("无障碍服务已启用，可以开始连点");
        }
    }
    
    private void startAutoClick() {
        if (isRunning) {
            Toast.makeText(this, "连点器已在运行", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // 获取选择的频率
        String frequency = frequencySpinner.getSelectedItem().toString();
        if (frequency.equals("1秒1次")) {
            clickInterval = 1000; // 1秒
        } else if (frequency.equals("1秒2次")) {
            clickInterval = 500; // 0.5秒
        }
        
        // 获取选择的持续时间
        String durationStr = durationSpinner.getSelectedItem().toString();
        duration = Integer.parseInt(durationStr.replace("秒", "")) * 1000;
        
        isRunning = true;
        startTime = System.currentTimeMillis();
        
        statusText.setText("连点器运行中...");
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
        
        // 开始点击
        handler.postDelayed(clickRunnable, clickInterval);
    }
    
    private void stopAutoClick() {
        isRunning = false;
        handler.removeCallbacks(clickRunnable);
        
        statusText.setText("连点器已停止");
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
    }
    
    private Runnable clickRunnable = new Runnable() {
        @Override
        public void run() {
            if (!isRunning) {
                return;
            }
            
            // 检查是否达到持续时间
            long elapsedTime = System.currentTimeMillis() - startTime;
            if (elapsedTime >= duration) {
                stopAutoClick();
                return;
            }
            
            // 执行点击
            performClick();
            
            // 继续下一次点击
            handler.postDelayed(this, clickInterval);
        }
    };
    
    private void performClick() {
        // 通过无障碍服务执行点击
        if (AutoClickService.getInstance() != null) {
            AutoClickService.getInstance().performGlobalClick();
        } else {
            Toast.makeText(this, "无障碍服务未启用", Toast.LENGTH_SHORT).show();
            stopAutoClick();
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopAutoClick();
    }
}