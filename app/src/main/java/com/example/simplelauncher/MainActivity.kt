package com.example.simplelauncher

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Bundle
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
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
        loadInstalledApps()
        setupClickListeners()
    }
    
    private fun setupUI() {
        // Setup app grid
        appAdapter = AppGridAdapter { appInfo ->
            launchApp(appInfo)
        }
        
        binding.appGrid.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 4)
            adapter = appAdapter
        }
        

    }
    
    private fun loadInstalledApps() {
        val packageManager = packageManager
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        
        val resolveInfoList = packageManager.queryIntentActivities(intent, 0)
        installedApps = resolveInfoList.map { resolveInfo ->
            AppInfo(
                packageName = resolveInfo.activityInfo.packageName,
                appName = resolveInfo.loadLabel(packageManager).toString(),
                icon = resolveInfo.loadIcon(packageManager),
                className = resolveInfo.activityInfo.name
            )
        }.sortedBy { it.appName }
        
        appAdapter.submitList(installedApps)
    }
    
    private fun setupClickListeners() {
        binding.appDrawerButton.setOnClickListener {
            openAppDrawer()
        }
        
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
    
    override fun onResume() {
        super.onResume()
        // Refresh app list if needed
        loadInstalledApps()
    }
}
