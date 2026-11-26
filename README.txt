Emulator Check Pro — Android Studio project
Target use: LDPlayer 14 (diagnostics only)

Build:
1. Install Android Studio with Android SDK Platform 35.
2. Open this folder in Android Studio.
3. Allow Gradle sync.
4. Build > Build Bundle(s) / APK(s) > Build APK(s).
5. Install the generated app-debug.apk in LDPlayer 14.

This app reads ordinary Android build properties and checks whether Google Play Services and Play Store packages are installed. It does not access or modify IMEI, spoof identifiers, conceal emulator status, or bypass app security controls.
