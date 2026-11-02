# NextSent Android

A WhatsApp-inspired **local messaging simulator** built with Java, Android SDK, and an embedded HTML/CSS/JavaScript interface.

NextSent is a standalone demo. It does not connect to WhatsApp, send real network messages, or place real phone calls.

## Features

- Familiar chat list with search across contacts and message history.
- Multiple selectable local accounts. Each account's sent messages appear as messages from that account in shared contact conversations.
- Preset fictional contacts and locally created groups.
- Profile name and About editing.
- Chat threads with simulated text, attachment labels, audio placeholders, and Google Maps location links.
- Starred, saved, and pinned message collections.
- Status updates that expire after 24 hours.
- Simulated outgoing calls with a ringing tone, no-answer result, and account switching that ends the call.
- Android camera app launch through a native bridge.
- Local browser storage for demo state.

## Important limitations

- Messages and accounts are stored only on this device/browser storage; there is no backend, real delivery, encryption, or cross-device sync.
- Contact names are fictional sample data, not real people or imported contacts.
- Image/audio/document attachments are represented by local demo labels; files are not uploaded or delivered to another person.
- Camera launch opens the device camera app, but captured photos are not yet imported into a chat.
- Calls are sound-and-screen simulations only. No person is actually called.
- Profile photos and full WhatsApp-level media handling are not implemented yet.
- The interface is inspired by common WhatsApp interaction patterns but uses NextSent branding and original assets.

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

The debug APK is generated at app/build/outputs/apk/debug/app-debug.apk.

## Project structure

- app/src/main/java/com/nextsent/android/MainActivity.java — Android activity and native camera/maps bridge.
- app/src/main/assets/index.html — NextSent interface and local simulator logic.
- app/src/main/AndroidManifest.xml — app declaration and launcher activity.
- app/src/main/res/layout/activity_main.xml — WebView host layout.

## Tech

- Java 17
- Android SDK 36
- Android Gradle Plugin 9.0.1
- WebView with local HTML, CSS and JavaScript
- LocalStorage for simulator data
