# NextSent Android

An Android messaging simulation app built with Java, Android SDK, and native XML layouts.

## Features

- Two-activity communication flow between **MainActivity** (Person 1) and **SecondActivity** (Person 2).
- Intent data passing (`EXTRA_HISTORY` & `EXTRA_MESSAGE`) with response handling (`startActivityForResult` / `setResult`).
- Real-time conversation message history log display.
- Persistent Light/Dark theme switching across activities via `ThemeManager`.
- Toggle switch (`SwitchMaterial`) for switching between Person 1 and Person 2 activities.

## Build

Open the repository in Android Studio and allow Gradle sync. Install Android SDK 36 and use Java 17.

Windows:

```bat
gradlew.bat assembleDebug
```

macOS/Linux:

```bash
./gradlew assembleDebug
```

The debug APK is generated at `app/build/outputs/apk/debug/app-debug.apk`.

## Project Structure

- `app/src/main/java/com/nextsent/android/MainActivity.java` — Main launcher activity (Person 1).
- `app/src/main/java/com/nextsent/android/SecondActivity.java` — Secondary activity (Person 2).
- `app/src/main/java/com/nextsent/android/ThemeManager.java` — Manages persistent Light/Dark theme state.
- `app/src/main/res/layout/activity_main.xml` — Native RelativeLayout UI for Person 1.
- `app/src/main/res/layout/activity_second.xml` — Native RelativeLayout UI for Person 2.
- `app/src/main/AndroidManifest.xml` — Application manifest registering both activities.

## Tech

- Java 17
- Android SDK 36
- Android Gradle Plugin 9.0.1
- Native Android XML Layouts
- Material Components (`SwitchMaterial`, `AppCompatDelegate`)
