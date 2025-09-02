package com.example.simplelauncher

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.simplelauncher.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivitySettingsBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupToolbar()
        
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings_container, SettingsFragment())
                .commit()
        }
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Launcher Settings"
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    
    class SettingsFragment : PreferenceFragmentCompat() {
        
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preferences, rootKey)
            
            setupPreferences()
        }
        
        private fun setupPreferences() {
            // Grid settings
            findPreference<androidx.preference.SeekBarPreference>("grid_columns")?.setOnPreferenceChangeListener { _, newValue ->
                // Update grid columns
                Toast.makeText(context, "Grid updated to ${newValue} columns", Toast.LENGTH_SHORT).show()
                true
            }
            
            // Theme settings
            findPreference<androidx.preference.SwitchPreferenceCompat>("dark_theme")?.setOnPreferenceChangeListener { _, newValue ->
                // Apply dark theme
                val isDark = newValue as Boolean
                Toast.makeText(context, 
                    if (isDark) "Dark theme enabled" else "Light theme enabled", 
                    Toast.LENGTH_SHORT).show()
                true
            }
            
            // Wallpaper settings
            findPreference<androidx.preference.SeekBarPreference>("wallpaper_opacity")?.setOnPreferenceChangeListener { _, newValue ->
                // Update wallpaper opacity
                Toast.makeText(context, "Wallpaper opacity updated", Toast.LENGTH_SHORT).show()
                true
            }
            
            // Gesture settings
            findPreference<androidx.preference.ListPreference>("swipe_up_gesture")?.setOnPreferenceChangeListener { _, newValue ->
                // Configure swipe up gesture
                Toast.makeText(context, "Swipe up gesture updated", Toast.LENGTH_SHORT).show()
                true
            }
        }
    }
}
