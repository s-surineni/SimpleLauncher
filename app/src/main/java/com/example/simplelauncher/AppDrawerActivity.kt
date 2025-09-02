package com.example.simplelauncher

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.simplelauncher.adapters.AppGridAdapter
import com.example.simplelauncher.databinding.ActivityAppDrawerBinding
import com.example.simplelauncher.models.AppInfo

class AppDrawerActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityAppDrawerBinding
    private lateinit var appAdapter: AppGridAdapter
    private var allApps: List<AppInfo> = emptyList()
    private var filteredApps: List<AppInfo> = emptyList()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupToolbar()
        setupRecyclerView()
        loadApps()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "App Drawer"
    }
    
    private fun setupRecyclerView() {
        appAdapter = AppGridAdapter { appInfo ->
            launchApp(appInfo)
        }
        
        binding.appGrid.apply {
            layoutManager = GridLayoutManager(this@AppDrawerActivity, 4)
            adapter = appAdapter
        }
    }
    
    private fun loadApps() {
        val packageManager = packageManager
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        
        val resolveInfoList = packageManager.queryIntentActivities(intent, 0)
        allApps = resolveInfoList.map { resolveInfo ->
            AppInfo(
                packageName = resolveInfo.activityInfo.packageName,
                appName = resolveInfo.loadLabel(packageManager).toString(),
                icon = resolveInfo.loadIcon(packageManager),
                className = resolveInfo.activityInfo.name,
                isSystemApp = resolveInfo.activityInfo.applicationInfo.flags and 
                    android.content.pm.ApplicationInfo.FLAG_SYSTEM != 0
            )
        }.sortedBy { it.appName }
        
        filteredApps = allApps
        appAdapter.submitList(filteredApps)
    }
    
    private fun launchApp(appInfo: AppInfo) {
        try {
            val intent = packageManager.getLaunchIntentForPackage(appInfo.packageName)
            intent?.let {
                startActivity(it)
                finish() // Close app drawer after launching app
            }
        } catch (e: Exception) {
            // Handle app launch error
        }
    }
    
    private fun filterApps(query: String) {
        filteredApps = if (query.isEmpty()) {
            allApps
        } else {
            allApps.filter { app ->
                app.appName.contains(query, ignoreCase = true) ||
                app.packageName.contains(query, ignoreCase = true)
            }
        }
        appAdapter.submitList(filteredApps)
    }
    
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.app_drawer_menu, menu)
        
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            
            override fun onQueryTextChange(newText: String?): Boolean {
                filterApps(newText ?: "")
                return true
            }
        })
        
        return true
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
