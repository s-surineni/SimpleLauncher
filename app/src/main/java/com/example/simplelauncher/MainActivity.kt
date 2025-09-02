package com.example.simplelauncher

import android.animation.ObjectAnimator
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simplelauncher.databinding.ActivityMainBinding
import com.example.simplelauncher.models.AppInfo
import com.example.simplelauncher.adapters.AppGridAdapter


class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var appAdapter: AppGridAdapter
    private var installedApps: List<AppInfo> = emptyList()
    private lateinit var gestureDetector: GestureDetector
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupGestureDetector()
        setupUI()
        loadInstalledApps()
        setupClickListeners()
        setupSwipeHint()
    }
    
    private fun setupGestureDetector() {
        gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                // Detect swipe up gesture
                if (e1 != null && e2 != null) {
                    val deltaY = e1.y - e2.y
                    val deltaX = e1.x - e2.x
                    
                    // Check if it's a vertical swipe up (deltaY > 0) and not too horizontal
                    if (deltaY > 100 && Math.abs(deltaX) < Math.abs(deltaY) && velocityY < -1000) {
                        openAppDrawer()
                        return true
                    }
                }
                return false
            }
        })
    }
    
    private fun setupUI() {
        // Setup app grid
        appAdapter = AppGridAdapter { appInfo ->
            launchApp(appInfo)
        }
        
        binding.appGrid.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 4) // 4 apps in a row
            adapter = appAdapter
        }
    }
    
    private fun setupSwipeHint() {
        // Fade out the swipe hint after 3 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            val fadeOut = ObjectAnimator.ofFloat(binding.swipeUpHint, "alpha", 0.7f, 0f)
            fadeOut.duration = 1000
            fadeOut.start()
        }, 3000)
    }
    
    private fun loadInstalledApps() {
        val packageManager = packageManager
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        
        val resolveInfoList = packageManager.queryIntentActivities(intent, 0)
        val allApps = resolveInfoList.map { resolveInfo ->
            AppInfo(
                packageName = resolveInfo.activityInfo.packageName,
                appName = resolveInfo.loadLabel(packageManager).toString(),
                icon = resolveInfo.loadIcon(packageManager),
                className = resolveInfo.activityInfo.name
            )
        }
        
        // Filter to show only specific apps: Phone, Messages, Chrome, Camera
        val targetApps = listOf(
            "com.android.dialer", // Phone
            "com.google.android.apps.messaging", // Messages
            "com.android.chrome", // Chrome
            "com.google.android.apps.camera" // Camera
        )
        
        installedApps = allApps.filter { app ->
            targetApps.contains(app.packageName) || 
            app.appName.equals("Phone", ignoreCase = true) ||
            app.appName.equals("Messages", ignoreCase = true) ||
            app.appName.equals("Chrome", ignoreCase = true) ||
            app.appName.equals("Camera", ignoreCase = true)
        }.sortedBy { app ->
            // Sort in the order: Phone, Messages, Chrome, Camera
            when {
                app.packageName.contains("dialer") || app.appName.equals("Phone", ignoreCase = true) -> 0
                app.packageName.contains("messaging") || app.appName.equals("Messages", ignoreCase = true) -> 1
                app.packageName.contains("chrome") || app.appName.equals("Chrome", ignoreCase = true) -> 2
                app.packageName.contains("camera") || app.appName.equals("Camera", ignoreCase = true) -> 3
                else -> 4
            }
        }
        
        appAdapter.submitList(installedApps)
    }
    
    private fun setupClickListeners() {
        binding.settingsButton.setOnClickListener {
            openSettings()
        }
        
        binding.wallpaperButton.setOnClickListener {
            openWallpaperPicker()
        }
    }
    
    private fun launchApp(appInfo: AppInfo) {
        try {
            val intent = packageManager.getLaunchIntentForPackage(appInfo.packageName)
            intent?.let {
                startActivity(it)
            }
        } catch (e: Exception) {
            // Handle app launch error
        }
    }
    
    private fun openAppDrawer() {
        val intent = Intent(this, AppDrawerActivity::class.java)
        startActivity(intent)
    }
    
    private fun openSettings() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }
    
    private fun openWallpaperPicker() {
        val intent = Intent(this, WallpaperPickerActivity::class.java)
        startActivity(intent)
    }
    
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event)
    }
    
    override fun onResume() {
        super.onResume()
        // Refresh app list if needed
        loadInstalledApps()
    }
}
