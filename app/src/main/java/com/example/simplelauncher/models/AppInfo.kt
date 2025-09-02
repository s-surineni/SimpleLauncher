package com.example.simplelauncher.models

import android.graphics.drawable.Drawable

data class AppInfo(
    val packageName: String,
    val appName: String,
    val icon: Drawable,
    val className: String,
    val isSystemApp: Boolean = false,
    val isFavorite: Boolean = false,
    val installTime: Long = 0L,
    val lastUsedTime: Long = 0L
)
