# NextSent Android - Module 6: Activities & Intents

This repository contains the complete implementation and reference guide for **Module 6: Activities & Intents** (Materi Mobile 6).

The original lecture slides are attached in [`docs/Materi_Mobile_6.pdf`](docs/Materi_Mobile_6.pdf).

---

## 📑 Table of Contents

1. [What is an Activity? (Slides 1–6)](#1-what-is-an-activity)
2. [4 Steps to Create an Activity (Slides 7–12)](#2-4-steps-to-create-an-activity)
3. [What is an Intent? (Slides 13–14)](#3-what-is-an-intent)
4. [Explicit vs. Implicit Intents (Slides 15–18)](#4-explicit-vs-implicit-intents)
5. [Passing Data via URIs & Extras (Slides 20–25)](#5-passing-data-via-uris--extras)
6. [Returning Data from Activity (Slides 26–30)](#6-returning-data-from-activity)
7. [Android Navigation: Back vs. Up (Slide 31)](#7-android-navigation-back-vs-up)
8. [Codebase Reference](#8-codebase-reference)

---

## 1. What is an Activity?

- An **Activity** is an application component that provides a window in which the app draws its user interface (UI).
- Represents a single screen and a single view hierarchy (written as a Java class extending `AppCompatActivity`).
- Handles user interactions (clicks, text input, login verification).
- Managed by a lifecycle: `Created` ➡️ `Started` ➡️ `Runs` ➡️ `Paused` ➡️ `Resumed` ➡️ `Stopped` ➡️ `Destroyed`.
- An app is composed of multiple activities bound together. The first screen displayed is the **Main Activity**.

---

## 2. 4 Steps to Create an Activity

```
[1. Create XML Layout] ➡️ [2. Create Java Class] ➡️ [3. Connect in onCreate] ➡️ [4. Declare in Manifest]
```

### 1. Create Layout XML (`res/layout/activity_main.xml`)
```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Let's Shop for Food!" />
</RelativeLayout>
```

### 2 & 3. Create Java Class & Connect Layout (`MainActivity.java`)
```java
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
```

### 4. Declare in `AndroidManifest.xml`
```xml
<activity android:name=".MainActivity" android:exported="true">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity>
```

---

## 3. What is an Intent?

An **Intent** is an asynchronous message object used to request an action from another app component or system service.

### Primary Functions of Intents:
1. **Start Activity**: Launch a new screen (e.g. text entry or sharing photos).
2. **Start Service**: Perform background operations (e.g. downloading files).
3. **Deliver Broadcast**: Receive system-wide events (e.g. battery low or device charging).

---

## 4. Explicit vs. Implicit Intents

| Type | Description | Code Example |
| :--- | :--- | :--- |
| **Explicit Intent** | Launches a specific Activity class directly within the app. | `Intent intent = new Intent(this, SecondActivity.class);`<br>`startActivity(intent);` |
| **Implicit Intent** | Asks the Android system to find an app that can handle the action. | `Uri uri = Uri.parse("http://www.google.com");`<br>`Intent intent = new Intent(Intent.ACTION_VIEW, uri);`<br>`startActivity(intent);` |

---

## 5. Passing Data via URIs & Extras

### A. Data (URI)
```java
// Set web page URL or File URI
intent.setData(Uri.parse("http://www.google.com"));
intent.setData(Uri.fromFile(new File("/sdcard/sample.jpg")));

// Retrieve URI in target Activity
Uri locationUri = getIntent().getData();
```

### B. Extras (Key-Value Pairs)
```java
// Sending Data in MainActivity
public static final String EXTRA_MESSAGE = "com.nextsent.android.extra.MESSAGE";
Intent intent = new Intent(this, SecondActivity.class);
intent.putExtra(EXTRA_MESSAGE, "Hello Activity!");
startActivity(intent);

// Receiving Data in SecondActivity
Intent intent = getIntent();
String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
```

---

## 6. Returning Data from Activity

When `SecondActivity` needs to return response data back to `MainActivity`:

```
[MainActivity] ──(startActivityForResult)──> [SecondActivity]
      ▲                                             │
      └──────────────(setResult & finish)───────────┘
```

### 1. Launching Activity for Result (`MainActivity.java`)
```java
public static final int TEXT_REQUEST = 1;

Intent intent = new Intent(this, SecondActivity.class);
intent.putExtra(EXTRA_MESSAGE, message);
startActivityForResult(intent, TEXT_REQUEST);
```

### 2. Returning Data & Closing (`SecondActivity.java`)
```java
Intent replyIntent = new Intent();
replyIntent.putExtra(EXTRA_REPLY, replyText);
setResult(RESULT_OK, replyIntent);
finish(); // Close activity and return
```

### 3. Handling Returned Result (`MainActivity.java`)
```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == TEXT_REQUEST && resultCode == RESULT_OK && data != null) {
        String reply = data.getStringExtra(SecondActivity.EXTRA_REPLY);
        // Process reply data...
    }
}
```

---

## 7. Android Navigation: Back vs. Up

1. **Back Navigation (Temporal Navigation)**:
   - Handled by the device back button / gesture.
   - Managed automatically by the system **back-stack**.

2. **Up Navigation (Ancestral Navigation)**:
   - Handled by the Up arrow in the app's Action Bar.
   - Defined via parent-child activity relationships in `AndroidManifest.xml`:
     ```xml
     <activity
         android:name=".SecondActivity"
         android:parentActivityName=".MainActivity" />
     ```

---

## 8. Codebase Reference

- **Main Screen**: `app/src/main/java/com/nextsent/android/MainActivity.java` & `app/src/main/res/layout/activity_main.xml`
- **Second Screen**: `app/src/main/java/com/nextsent/android/SecondActivity.java` & `app/src/main/res/layout/activity_second.xml`
- **App Manifest**: `app/src/main/AndroidManifest.xml`
