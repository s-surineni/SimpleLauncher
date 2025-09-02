package com.example.simplelauncher

import android.app.WallpaperManager
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.simplelauncher.databinding.ActivityWallpaperPickerBinding
import java.io.IOException

class WallpaperPickerActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityWallpaperPickerBinding
    private lateinit var wallpaperManager: WallpaperManager
    
    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { setWallpaperFromUri(it) }
    }
    
    private val takePhotoLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            // Photo taken successfully, can be set as wallpaper
            Toast.makeText(this, "Photo captured! Set as wallpaper.", Toast.LENGTH_SHORT).show()
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWallpaperPickerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupToolbar()
        setupWallpaperManager()
        setupClickListeners()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Wallpaper Picker"
    }
    
    private fun setupWallpaperManager() {
        wallpaperManager = WallpaperManager.getInstance(this)
    }
    
    private fun setupClickListeners() {
        binding.galleryButton.setOnClickListener {
            pickImageFromGallery()
        }
        
        binding.cameraButton.setOnClickListener {
            takePhoto()
        }
        
        binding.defaultWallpapersButton.setOnClickListener {
            showDefaultWallpapers()
        }
        
        binding.colorWallpaperButton.setOnClickListener {
            showColorPicker()
        }
    }
    
    private fun pickImageFromGallery() {
        pickImageLauncher.launch("image/*")
    }
    
    private fun takePhoto() {
        // This would require camera permissions and a file provider
        Toast.makeText(this, "Camera functionality requires additional setup", Toast.LENGTH_SHORT).show()
    }
    
    private fun setWallpaperFromUri(uri: Uri) {
        try {
            val inputStream = contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()
            
            if (bitmap != null) {
                wallpaperManager.setBitmap(bitmap)
                Toast.makeText(this, "Wallpaper set successfully!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show()
            }
        } catch (e: IOException) {
            Toast.makeText(this, "Error setting wallpaper: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun showDefaultWallpapers() {
        // Show built-in wallpapers
        Toast.makeText(this, "Default wallpapers feature coming soon", Toast.LENGTH_SHORT).show()
    }
    
    private fun showColorPicker() {
        // Show color picker for solid color wallpapers
        Toast.makeText(this, "Color picker feature coming soon", Toast.LENGTH_SHORT).show()
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
