# NextSent — The Self-Messaging Social Network

> **For the unemployed, the jobless, and the beautifully unoccupied.** No coworkers? No problem. Message yourself. Create group chats with yourself. Have a meeting with yourself. Finally, someone who understands you.

NextSent is a tongue-in-cheek messaging app for people with plenty of free time and absolutely nobody demanding a status update. It is a playful, local messaging simulator: your contacts are fictional, your conversations stay on your device, and you can switch between multiple accounts to hold a lively discussion with yourself.

**Tagline:** Your time is free. Your inbox doesn't have to be empty.

## Version 2.0.0

This release introduces the WhatsApp-inspired NextSent redesign, with a familiar chat-first layout and NextSent branding.

## Features

- Familiar chat list with search across contacts and message history.
- Multiple selectable local accounts. Each account's messages appear in shared contact conversations, so you can stage a conversation between your own accounts.
- Preset fictional contacts and locally created groups.
- Profile name and About editing.
- Chat threads with simulated text, attachment labels, audio placeholders, and Google Maps location links.
- Starred, saved, and pinned message collections.
- Status updates that expire after 24 hours.
- Simulated outgoing calls with a ringing tone, no-answer result, and account switching that ends the call.
- Android camera app launch through a native bridge.
- Local browser storage for demo state.

## Important limitations

- This is a local simulator, not a real messaging service. It does not connect to WhatsApp or deliver messages to other people.
- Messages and accounts are stored only in this app's local WebView storage; there is no backend, real delivery, end-to-end encryption, or cross-device sync.
- Contact names are fictional sample data, not real people or imported contacts.
- Image/audio/document attachments are represented by local demo labels; files are not uploaded or delivered.
- Camera launch opens the device camera app, but captured photos are not yet imported into a chat.
- Calls are sound-and-screen simulations only. No person is actually called.
- Profile photo selection and full WhatsApp-level media handling are not implemented.
- The interface is inspired by common messaging-app interaction patterns while retaining NextSent branding and original assets.

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

## Project structure

- `app/src/main/java/com/nextsent/android/MainActivity.java` — Android activity and native camera/maps bridge.
- `app/src/main/assets/index.html` — NextSent interface and local simulator logic.
- `app/src/main/AndroidManifest.xml` — app declaration and launcher activity.
- `app/src/main/res/layout/activity_main.xml` — WebView host layout.

## Tech

- Java 17
- Android SDK 36
- Android Gradle Plugin 9.0.1
- Gradle 9.1.0
- WebView with local HTML, CSS and JavaScript
- LocalStorage for simulator data
