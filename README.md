# ITS Tracking SDK for Android v1.1.x

## Overview
**ITS Tracking SDK** is an in-app event tracking SDK for Android applications, enabling the collection and analysis of user interaction data to optimize app performance and user experience.
## Features
- 📊 **Advanced Analytics**: Track in-app events and conduct comprehensive user behavior analysis across mobile applications.
- 🔒 **Privacy Compliant**: Built with user privacy and data protection in mind
- 🚀 **Easy Integration**: Simple and straightforward SDK initialization
- 📱 **Android Native**: Optimized for Android platform performance
- 🔐 **Secure Data Transmission**: Enterprise-grade data security
- 📈 **Real-time Tracking**: Monitor user activities and app events in real-time

## System Requirements

- **Minimum SDK**: Android API 26 (Android 8.0)
- **Target SDK**: Android API 36
- **Compile SDK**: Android API 36
- **Java Version**: Java 17 or higher
- **Gradle**: 8.1 or higher
- **Android Gradle Plugin**: 8.1 or higher
- **google-services**: 4.4.1 or higher

## Installation

### 1. Add Dependencies to Project-Level `build.gradle`

In your root-level (project-level) Gradle file `<project>/build.gradle`:

```gradle
buildscript {
    dependencies {
        //...
        classpath 'com.google.gms:google-services:4.4.1' //or higher
        //...
    }
}
```
### 2. Place SDK Files
1. Download the ITS Tracking SDK (`.aar` file)
2. Create a `libs` folder in your app module directory if it doesn't exist: `<project>/<app-module>/libs/`
3. Copy the SDK `.aar` file into the `libs` folder
   
### 3. Add Dependencies to Application Module-Level `build.gradle`

In your module (app-module,launcher-module) Gradle file `<project>/<app-module>/build.gradle`:

```gradle
dependencies {

    //...
    // ITS Tracking SDK
    implementation files('libs/its_sdk_1.1.2.aar') // demo for version 1.1.2, same lib file name downloaded in lib folder
    // Required Dependencies
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    implementation 'com.google.android.material:material:1.11.0'
    implementation 'androidx.core:core:1.12.0'
    implementation 'androidx.browser:browser:1.8.0'
    // Lifecycle Components
    implementation 'androidx.lifecycle:lifecycle-process:2.7.0'
    implementation 'androidx.lifecycle:lifecycle-common:2.7.0'
    // Network & Data
    compileOnly 'org.apache.tomcat:annotations-api:6.0.53'
    // Database
    implementation 'net.zetetic:sqlcipher-android:4.10.0@aar' // Updated for v2.0.1 (16KB page size compatibility)
    implementation 'androidx.sqlite:sqlite:2.4.0'
    // Analytics & Tracking
    implementation 'com.google.firebase:firebase-analytics:21.5.0'
    implementation 'com.rudderstack.android.sdk:core:1.25.1'
    implementation 'com.android.installreferrer:installreferrer:2.2'
    //...
}
```
### 4. AndroidManifest Configuration

Add the following permissions to your `AndroidManifest.xml`:

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <!-- Required Permissions -->
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <!-- Your Activities -->
    <!-- //... -->
    </application>
</manifest>
```

### 5. Backup Rules Configuration

To ensure accurate tracking data, add backup rules to exclude SDK preferences from Android Auto Backup:

**For Android 11 and below**, create `app/src/main/res/xml/backup_rules_11.xml`:
```xml
<?xml version="1.0" encoding="utf-8"?>
<full-backup-content>
    <exclude domain="sharedpref" path="its_prefs.xml"/>
    <exclude domain="sharedpref" path="rl_prefs.xml"/>
</full-backup-content>
```

**For Android 12 and above**, create `app/src/main/res/xml/backup_rules_12.xml`:
```xml
<?xml version="1.0" encoding="utf-8"?>
<data-extraction-rules>
    <cloud-backup>
        <exclude domain="sharedpref" path="its_prefs.xml"/>
        <exclude domain="sharedpref" path="rl_prefs.xml"/>
    </cloud-backup>
    <device-transfer>
        <exclude domain="sharedpref" path="its_prefs.xml"/>
        <exclude domain="sharedpref" path="rl_prefs.xml"/>
    </device-transfer>
</data-extraction-rules>
```

Then reference these files in your `AndroidManifest.xml`:
```xml
<application
    android:fullBackupContent="@xml/backup_rules_11"
    android:dataExtractionRules="@xml/backup_rules_12">
    <!-- Your app content -->
</application>
```

**Note**: These backup rules prevent SDK tracking data from being restored on new devices, ensuring accurate user analytics and preventing duplicate tracking events.
## Quick Start
     
### 1. Initialize SDK
* Initializes the ITS SDK with the provided `its_writekey_value` and `its_signingkey_value`.
* **Note**: These keys are configured on the ITS Dashboard.

```java
import android.app.Application;
import com.gosu.itc.its.ITSSdk;
import com.gosu.itc.its.inteface.iTSListener;
import com.gosu.itc.its.utils.SdkException;
import org.json.JSONObject;
import org.json.JSONException;

public class MyApplication extends Application {
    ITSSdk itsSdk;
    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize ITS Tracking SDK
        ITSSdk itsSdk = ITSSdk.getInstance();

        // Add custom info that should be tracked across all events
        JSONObject customInfo = new JSONObject();
        try {
            customInfo.put("version", "1.1.2");
        } catch (JSONException e) {
            Log.e("ITS_TAG", "Error creating customInfo JSON:" + e);
        }
        //
        itsSdk.initSdk(this, "its_writekey_value", "its_signingkey_value", customInfo, new iTSListener() {
            @Override
            public void OnInitializeSuccess() {
                Log.d("ITS_TAG", "SDK initialized successfully");
            }

            @Override
            public void onInitializeError(SdkException exception) {
                Log.e("ITS_TAG", "SDK initialization failed: " + exception.getMessage());
            }
            // This is a feature planned for a future release; implementation is optional
            @Override
            public void onNotifyNewTask(int i) {
                
            }
        });
    }

     @Override
    protected void onResume() {
        super.onResume();
        //hooks OnResume events
        itsSdk.onActivityResumeListener();
    }
}
```

### 2. Track Events in Your Applications
The SDK provides predefined methods for tracking common gaming events:
Demo Tracking : 
```java
    //Tracking login event of user
    ITSSdk.getInstance().login("user_id", "user_name", "userEmail");
```

## API Reference

For comprehensive API documentation and detailed tracking guide, please refer to: **[TRACKING_GUIDE.md](TRACKING_GUIDE.md)**

The tracking guide includes:
- 📚 Complete API reference for all SDK methods
- 🎮 Gaming-specific event tracking examples
- 🔄 Lifecycle event management
- 📋 Task management features
- 💡 Best practices and implementation tips

**Issue**: SDK initialization fails
- **Solution**: Check your App ID and API Key are correct
- **Solution**: Verify internet permission is granted
- **Solution**: Check network connectivity

## Privacy & Data Protection

ITS Tracking SDK is designed with privacy in mind:

- 🔒 All data is encrypted in transit
- 💾 Local data is stored securely using SQLCipher
- 👤 User data can be cleared on demand
- ✅ GDPR compliant architecture
- 🛡️ No personal data collected without explicit tracking calls

## Support

For issues, questions, or feature requests:

- 📧 Email: support@its-tracking.com
- 📝 Documentation: https://docs.its-tracking.com
- 🐛 Issues: https://github.com/your-repo/android-itssdk/issues

## License

Copyright © 2026 ITS Tracking. All rights reserved.

## Changelog

### Version 1.1.1

- ✅ **Tracking version SDK**: Added SDK version tracking for better analytics and debugging
- ⚡ **Update polling time**: Optimized data polling intervals for improved performance
- 🐛 **Fix bug missing app_mode**: Resolved issue where app_mode parameter was not being properly retained

### Version 1.x.x (Current)

- 🎉 Initial release
- ✅ In-app event tracking
- ✅ Recommended In-app events in Gaming
- ✅ User property management
- ✅ Secure data storage with SQLCipher
- ✅ Android 8.0+ support

---
