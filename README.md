# Simple Launcher

A custom Android launcher that provides a clean, minimalist home screen experience with essential functionality.

## Features

### 🏠 **Home Screen**
- Clean, transparent interface that shows your wallpaper
- App grid with customizable columns (3-6 columns)
- Quick access to settings and wallpaper picker
- Time display in the top-left corner

### 📱 **App Drawer**
- Full-screen app drawer accessible via the center button
- Search functionality to quickly find apps
- Alphabetically sorted app list
- Grid layout for easy navigation

### ⚙️ **Settings & Customization**
- Grid layout customization
- Dark/light theme toggle
- Wallpaper opacity adjustment
- Gesture customization (swipe up actions)
- User preferences storage

### 🖼️ **Wallpaper Management**
- Choose from gallery
- Camera capture (requires additional setup)
- Built-in wallpaper options
- Solid color wallpapers

## Project Structure

```
SimpleLauncher/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/simplelauncher/
│   │   │   ├── MainActivity.kt              # Home screen
│   │   │   ├── AppDrawerActivity.kt         # App drawer
│   │   │   ├── SettingsActivity.kt          # Settings
│   │   │   ├── WallpaperPickerActivity.kt   # Wallpaper picker
│   │   │   ├── adapters/
│   │   │   │   └── AppGridAdapter.kt        # App grid adapter
│   │   │   └── models/
│   │   │       └── AppInfo.kt               # App data model
│   │   ├── res/
│   │   │   ├── layout/                      # UI layouts
│   │   │   ├── drawable/                    # Icons and graphics
│   │   │   ├── values/                      # Colors, themes, strings
│   │   │   ├── xml/                         # Preferences
│   │   │   └── menu/                        # Menu resources
│   │   └── AndroidManifest.xml              # App manifest
│   └── build.gradle                         # App module build config
├── build.gradle                             # Project build config
├── settings.gradle                          # Project settings
├── gradle.properties                        # Gradle properties
└── README.md                                # This file
```

## Key Components

### MainActivity
The main launcher activity that serves as the home screen. It displays:
- App grid with installed applications
- Quick action buttons (settings, wallpaper)
- App drawer access button
- Transparent background to show wallpaper

### AppDrawerActivity
Full-screen app drawer that shows all installed apps with:
- Search functionality
- Grid layout
- App icons and names
- Quick app launching

### SettingsActivity
Preference-based settings screen with:
- Grid customization
- Theme selection
- Wallpaper settings
- Gesture configuration

### AppGridAdapter
RecyclerView adapter for displaying apps in a grid format with:
- Efficient view recycling
- Click and long-press handling
- DiffUtil for smooth updates

## Setup Instructions

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK 24+ (Android 7.0+)
- Kotlin support enabled

### Building the Project
1. Clone or download the project
2. Open in Android Studio
3. Sync Gradle files
4. Build the project (Build → Make Project)

### Installing on Device
1. Enable "Developer Options" on your Android device
2. Enable "USB Debugging"
3. Connect device to computer
4. Run the app from Android Studio

### Setting as Default Launcher
1. Install the app
2. Press the home button
3. Select "Simple Launcher" from the launcher chooser
4. Choose "Always" to set as default

## Customization

### Grid Layout
- Modify `grid_columns` preference in settings
- Adjust item spacing in `item_app_grid.xml`
- Change grid layout manager in activities

### Themes
- Customize colors in `colors.xml`
- Modify themes in `themes.xml`
- Add new theme variants as needed

### Icons
- Replace drawable resources in `drawable/` folder
- Use vector drawables for scalability
- Maintain consistent icon sizing

## Permissions

The launcher requires these permissions:
- `QUERY_ALL_PACKAGES`: To discover installed apps
- `SET_WALLPAPER`: To change device wallpaper
- `READ_EXTERNAL_STORAGE`: To access gallery images
- `WRITE_EXTERNAL_STORAGE`: To save wallpaper changes
- `SYSTEM_ALERT_WINDOW`: For overlay functionality

## Architecture

The project follows modern Android development practices:
- **MVVM Architecture**: Using ViewModels and LiveData
- **View Binding**: Type-safe view access
- **Kotlin**: Modern language features and null safety
- **Material Design**: Following Material Design guidelines
- **RecyclerView**: Efficient list/grid display
- **Preferences**: Settings management with PreferenceFragmentCompat

## Dependencies

- **AndroidX Core**: Core Android functionality
- **Material Components**: Material Design components
- **RecyclerView**: Efficient list/grid display
- **Lifecycle**: ViewModels and LiveData
- **Preferences**: Settings management
- **Room**: Local database (for future features)

## Future Enhancements

- [ ] Widget support
- [ ] Folder organization
- [ ] Custom app icons
- [ ] Gesture navigation
- [ ] Backup/restore settings
- [ ] Multiple home screens
- [ ] App usage statistics
- [ ] Custom themes
- [ ] Animation effects

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is open source and available under the [MIT License](LICENSE).

## Support

For issues and questions:
- Check existing issues in the repository
- Create a new issue with detailed information
- Include device information and Android version

---

**Note**: This is a custom launcher that replaces your device's default home screen. Make sure to test thoroughly before setting as default, and keep your default launcher accessible in case of issues.
