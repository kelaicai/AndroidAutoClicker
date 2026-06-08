package com.example.autoclicker;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.graphics.Path;
import android.os.Build;
import android.view.accessibility.AccessibilityEvent;

public class AutoClickService extends AccessibilityService {
    
    private static AutoClickService instance;
    
    public static AutoClickService getInstance() {
        return instance;
    }
    
    @Override
    public void onServiceConnected() {
        super.onServiceConnected();
        instance = this;
    }
    
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // 不需要处理事件
    }
    
    @Override
    public void onInterrupt() {
        // 服务中断时的处理
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        instance = null;
    }
    
    /**
     * 执行全局点击（屏幕中心位置）
     */
    public void performGlobalClick() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // 获取屏幕尺寸
            int screenWidth = getResources().getDisplayMetrics().widthPixels;
            int screenHeight = getResources().getDisplayMetrics().heightPixels;
            
            // 计算屏幕中心位置
            int x = screenWidth / 2;
            int y = screenHeight / 2;
            
            performClick(x, y);
        }
    }
    
    /**
     * 在指定位置执行点击
     */
    public void performClick(int x, int y) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Path clickPath = new Path();
            clickPath.moveTo(x, y);
            
            GestureDescription gestureDescription = new GestureDescription.Builder()
                    .addStroke(new GestureDescription.StrokeDescription(clickPath, 0, 100))
                    .build();
            
            dispatchGesture(gestureDescription, null, null);
        }
    }
    
    /**
     * 执行长按
     */
    public void performLongClick(int x, int y, long duration) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Path clickPath = new Path();
            clickPath.moveTo(x, y);
            
            GestureDescription gestureDescription = new GestureDescription.Builder()
                    .addStroke(new GestureDescription.StrokeDescription(clickPath, 0, duration))
                    .build();
            
            dispatchGesture(gestureDescription, null, null);
        }
    }
    
    /**
     * 执行滑动
     */
    public void performSwipe(int startX, int startY, int endX, int endY, long duration) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Path swipePath = new Path();
            swipePath.moveTo(startX, startY);
            swipePath.lineTo(endX, endY);
            
            GestureDescription gestureDescription = new GestureDescription.Builder()
                    .addStroke(new GestureDescription.StrokeDescription(swipePath, 0, duration))
                    .build();
            
            dispatchGesture(gestureDescription, null, null);
        }
    }
}