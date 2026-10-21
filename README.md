# NextSent Android - Complete Learning & Implementation Guide

Welcome to **NextSent Android**! This repository serves as both a complete theoretical guide and a practical implementation project for **Module 1 (Activities & Intents Lecture)** and **Module 2 (Facebook-Style Two-Way Messaging Project)**.

The lecture slides document is attached in [`docs/Materi_Mobile_6.pdf`](docs/Materi_Mobile_6.pdf).

---

## 📑 Table of Contents

1. [MODULE 1: Activities & Intents (Comprehensive Theory)](#module-1-activities--intents-comprehensive-theory)
   - [1.1 What is an Activity? (Slides 1–6)](#11-what-is-an-activity-slides-16)
   - [1.2 4-Step Activity Creation Workflow (Slides 7–12)](#12-4-step-activity-creation-workflow-slides-712)
   - [1.3 What is an Intent? (Slides 13–14)](#13-what-is-an-intent-slides-1314)
   - [1.4 Explicit vs. Implicit Intents (Slides 15–18)](#14-explicit-vs-implicit-intents-slides-1518)
   - [1.5 Passing Data via URIs & Extras (Slides 20–25)](#15-passing-data-via-uris--extras-slides-2025)
   - [1.6 Returning Data with `startActivityForResult` (Slides 26–30)](#16-returning-data-with-startactivityforresult-slides-2630)
   - [1.7 Navigation: Back vs. Up Navigation (Slide 31)](#17-navigation-back-vs-up-navigation-slide-31)
2. [MODULE 2: Facebook-Style Messaging App (Hands-On Implementation)](#module-2-facebook-style-messaging-app-hands-on-implementation)
   - [2.1 Task Goal & Architecture](#21-task-goal--architecture)
   - [2.2 Activity 1 (`MainActivity`) Layout & Code](#22-activity-1-mainactivity-layout--code)
   - [2.3 Activity 2 (`SecondActivity`) Layout & Code](#23-activity-2-secondactivity-layout--code)
   - [2.4 Two-Way Message Flow Summary](#24-two-way-message-flow-summary)
3. [Catatan Activity & Intent: Cara Activity 2 Mengirim Balasan ke Activity 1](#catatan-activity--intent-cara-activity-2-mengirim-balasan-ke-activity-1)
4. [Codebase Reference & Build Guide](#codebase-reference--build-guide)

---

# MODULE 1: Activities & Intents (Comprehensive Theory)

### 1.1 What is an Activity? (Slides 1–6)
- **Definition**: An **Activity** is a core app component that provides a window in which the application draws its user interface (UI).
- **Structure**: Represents a single screen and single view hierarchy, implemented as a Java class extending `AppCompatActivity`.
- **Purpose**: Handles user interactions such as button clicks, text entry, or login verification.
- **Lifecycle**: Managed by the Android runtime through lifecycle callbacks:
  ```
  [Created] ➡️ [Started] ➡️ [Runs / Resumed] ➡️ [Paused] ➡️ [Stopped] ➡️ [Destroyed]
  ```
- **Main Activity**: The primary screen launched when tapping the app icon, registered in `AndroidManifest.xml` with:
  ```xml
  <intent-filter>
      <action android:name="android.intent.action.MAIN" />
      <category android:name="android.intent.category.LAUNCHER" />
  </intent-filter>
  ```

---

### 1.2 4-Step Activity Creation Workflow (Slides 7–12)

```
┌────────────────────┐     ┌─────────────────────┐     ┌──────────────────────┐     ┌───────────────────────┐
│ 1. Create Layout   │ ──► │ 2. Create Java      │ ──► │ 3. Connect Layout    │ ──► │ 4. Declare Activity   │
│    XML File        │     │    Activity Class   │     │    in onCreate()     │     │    in Manifest        │
└────────────────────┘     └─────────────────────┘     └──────────────────────┘     └───────────────────────┘
```

1. **Create XML Layout** (`res/layout/activity_main.xml`):
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

2. **Create Java Activity Class**:
   ```java
   public class MainActivity extends AppCompatActivity { ... }
   ```

3. **Connect Layout in `onCreate()`**:
   ```java
   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
   }
   ```

4. **Declare in `AndroidManifest.xml`**:
   ```xml
   <activity android:name=".MainActivity" android:exported="true">
       <intent-filter>
           <action android:name="android.intent.action.MAIN" />
           <category android:name="android.intent.category.LAUNCHER" />
       </intent-filter>
   </activity>
   ```

---

### 1.3 What is an Intent? (Slides 13–14)

An **Intent** is an asynchronous messaging object used to request an action from another app component or system service.

#### 3 Core Uses of Intents:
1. **Start an Activity**: Launch a new screen for text input, sharing, or ordering items.
2. **Start a Service**: Perform background tasks like downloading files.
3. **Deliver a Broadcast**: Send system-wide notifications (e.g., battery low or charger connected).

---

### 1.4 Explicit vs. Implicit Intents (Slides 15–18)

| Feature | Explicit Intent | Implicit Intent |
| :--- | :--- | :--- |
| **Definition** | Specifies the exact target Activity class inside the app. | Asks the Android system to find an app that can handle the action. |
| **Use Case** | Navigating between screens within the same app. | Opening web links or dialing phone numbers via system apps. |
| **Code Example** | `Intent intent = new Intent(this, SecondActivity.class);`<br>`startActivity(intent);` | `Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));`<br>`startActivity(intent);` |

#### Implicit Intent Examples:
```java
// Open a Website
Uri webUri = Uri.parse("http://www.google.com");
Intent webIntent = new Intent(Intent.ACTION_VIEW, webUri);
startActivity(webIntent);

// Open Phone Dialer
Uri phoneUri = Uri.parse("tel:08005551234");
Intent phoneIntent = new Intent(Intent.ACTION_DIAL, phoneUri);
startActivity(phoneIntent);
```

---

### 1.5 Passing Data via URIs & Extras (Slides 20–25)

#### Method A: Passing URIs (`setData`)
```java
// Setting Data URI
intent.setData(Uri.parse("http://www.google.com"));
intent.setData(Uri.fromFile(new File("/sdcard/sample.jpg")));

// Getting Data URI in receiving Activity
Uri locationUri = getIntent().getData();
```

#### Method B: Passing Extras (`putExtra`)
```java
// 1. Declare Key in Sender Activity
public static final String EXTRA_MESSAGE = "com.nextsent.android.MESSAGE";

// 2. Attach Extra payload
Intent intent = new Intent(this, SecondActivity.class);
String message = "Hello Activity!";
intent.putExtra(EXTRA_MESSAGE, message);
startActivity(intent);

// 3. Receive Extra payload in Receiver Activity
Intent intent = getIntent();
String receivedMessage = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
```

---

### 1.6 Returning Data with `startActivityForResult` (Slides 26–30)

```
[MainActivity] ───(startActivityForResult)───► [SecondActivity]
      ▲                                               │
      └────────────────(setResult & finish)───────────┘
```

1. **Launch for Result in `MainActivity`**:
   ```java
   public static final int TEXT_REQUEST = 1;
   Intent intent = new Intent(this, SecondActivity.class);
   intent.putExtra(EXTRA_MESSAGE, message);
   startActivityForResult(intent, TEXT_REQUEST);
   ```

2. **Return Data in `SecondActivity`**:
   ```java
   Intent replyIntent = new Intent();
   replyIntent.putExtra(EXTRA_REPLY, replyText);
   setResult(RESULT_OK, replyIntent);
   finish(); // Closes SecondActivity and returns to MainActivity
   ```

3. **Handle Result in `MainActivity`**:
   ```java
   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       super.onActivityResult(requestCode, resultCode, data);
       if (requestCode == TEXT_REQUEST && resultCode == RESULT_OK && data != null) {
           String reply = data.getStringExtra(SecondActivity.EXTRA_REPLY);
           // Display reply text...
       }
   }
   ```

---

### 1.7 Navigation: Back vs. Up Navigation (Slide 31)

1. **Back Navigation (Temporal)**: Handled by the device back button / gesture and managed automatically by the Android system **back-stack**.
2. **Up Navigation (Ancestral)**: Handled by the Up arrow in the Action Bar, configured by setting parent-child relationships in `AndroidManifest.xml`:
   ```xml
   <activity
       android:name=".SecondActivity"
       android:parentActivityName=".MainActivity" />
   ```

---

# MODULE 2: Facebook-Style Messaging App (Hands-On Implementation)

### 2.1 Task Goal & Architecture

Create a simple two-way messaging application inspired by Facebook Messages using **2 Activities** and **RelativeLayout**:
- **Activity 1 (`MainActivity`)**: Contains an `EditText` for message input and a `Button` aligned at the bottom (`android:layout_alignParentBottom="true"`). When clicked, it passes the text to Activity 2 via `putExtra`.
- **Activity 2 (`SecondActivity`)**: Displays a Header and the message received from Activity 1. Allows typing a reply in an `EditText` and sending it back using `setResult(RESULT_OK, replyIntent)` and `finish()`.

---

### 2.2 Activity 1 (`MainActivity`) Layout & Code

#### Layout (`res/layout/activity_main.xml`)
```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="16dp"
    tools:context=".MainActivity">

    <TextView
        android:id="@+id/textViewHeader"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentTop="true"
        android:layout_centerHorizontal="true"
        android:text="@string/header_main"
        android:textSize="22sp"
        android:textStyle="bold" />

    <!-- Reply Display Area -->
    <LinearLayout
        android:id="@+id/layoutReply"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_below="@id/textViewHeader"
        android:layout_marginTop="24dp"
        android:orientation="vertical">

        <TextView
            android:id="@+id/textViewReplyHeader"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/text_header_reply"
            android:textSize="16sp"
            android:textStyle="bold"
            android:visibility="gone" />

        <TextView
            android:id="@+id/textViewReply"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="4dp"
            android:textSize="18sp"
            android:visibility="gone" />
    </LinearLayout>

    <!-- Implicit Intent Button -->
    <Button
        android:id="@+id/buttonWeb"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        android:onClick="openWeb"
        android:text="@string/button_open_web" />

    <!-- RelativeLayout Input Controls -->
    <EditText
        android:id="@+id/editText"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_toLeftOf="@id/button"
        android:layout_alignParentBottom="true"
        android:hint="@string/hint_enter_message"
        android:inputType="text" />

    <Button
        android:id="@+id/button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentRight="true"
        android:layout_alignParentBottom="true"
        android:onClick="openSecondActivity"
        android:text="@string/button_send" />

</RelativeLayout>
```

#### Code (`MainActivity.java`)
```java
package com.nextsent.android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.nextsent.android.MESSAGE";
    public static final int TEXT_REQUEST = 1;

    private EditText editText;
    private TextView textViewReplyHeader;
    private TextView textViewReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        textViewReplyHeader = findViewById(R.id.textViewReplyHeader);
        textViewReply = findViewById(R.id.textViewReply);
    }

    // Triggered by android:onClick="openSecondActivity" in XML
    public void openSecondActivity(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivityForResult(intent, TEXT_REQUEST);
    }

    // Triggered by android:onClick="openWeb" in XML
    public void openWeb(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.google.com"));
        startActivity(intent);
    }

    // Handles reply payload returned from SecondActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TEXT_REQUEST && resultCode == RESULT_OK && data != null) {
            String reply = data.getStringExtra(SecondActivity.EXTRA_REPLY);
            textViewReplyHeader.setVisibility(View.VISIBLE);
            textViewReply.setText(reply);
            textViewReply.setVisibility(View.VISIBLE);
        }
    }
}
```

---

### 2.3 Activity 2 (`SecondActivity`) Layout & Code

#### Layout (`res/layout/activity_second.xml`)
```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="16dp"
    tools:context=".SecondActivity">

    <!-- Received Message Container -->
    <LinearLayout
        android:id="@+id/layoutReceived"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentTop="true"
        android:layout_marginTop="24dp"
        android:orientation="vertical">

        <TextView
            android:id="@+id/textViewMessageHeader"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/text_header_received"
            android:textSize="16sp"
            android:textStyle="bold" />

        <TextView
            android:id="@+id/textViewMessage"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="4dp"
            android:textSize="18sp" />
    </LinearLayout>

    <!-- RelativeLayout Input Controls -->
    <EditText
        android:id="@+id/editTextReply"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_toLeftOf="@id/buttonReply"
        android:layout_alignParentBottom="true"
        android:hint="@string/hint_enter_reply"
        android:inputType="text" />

    <Button
        android:id="@+id/buttonReply"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentRight="true"
        android:layout_alignParentBottom="true"
        android:onClick="returnReply"
        android:text="@string/button_reply" />

</RelativeLayout>
```

#### Code (`SecondActivity.java`)
```java
package com.nextsent.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.nextsent.android.REPLY";

    private EditText editTextReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView textViewMessage = findViewById(R.id.textViewMessage);
        editTextReply = findViewById(R.id.editTextReply);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(MainActivity.EXTRA_MESSAGE)) {
            String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
            if (message != null && !message.isEmpty()) {
                textViewMessage.setText(message);
            }
        }
    }

    // Triggered by android:onClick="returnReply" in XML
    public void returnReply(View view) {
        String reply = editTextReply.getText().toString();
        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_REPLY, reply);
        setResult(RESULT_OK, replyIntent);
        finish();
    }
}
```

---

### 2.4 Two-Way Message Flow Summary

```
   ┌─────────────────────────────────────────────────────────────┐
   │                       MainActivity                          │
   │  [EditText] ──(openSecondActivity / putExtra)──► [Button]   │
   └──────────────────────────────┬──────────────────────────────┘
                                  │
                                  ▼
   ┌─────────────────────────────────────────────────────────────┐
   │                       SecondActivity                        │
   │  [TextView] ◄────────(Message Display)                      │
   │  [EditText] ──(returnReply / setResult / finish)──►[Button] │
   └──────────────────────────────┬──────────────────────────────┘
                                  │
                                  ▼
   ┌─────────────────────────────────────────────────────────────┐
   │                       MainActivity                          │
   │  [TextView] ◄────────(onActivityResult / Reply Display)     │
   └─────────────────────────────────────────────────────────────┘
```

---

# Catatan Activity & Intent: Cara Activity 2 Mengirim Balasan ke Activity 1

### 1. Konsep Dasar
Intent digunakan untuk berpindah dari satu Activity ke Activity lainnya sekaligus membawa data atau pesan.
- **Activity 1 (`MainActivity`)**: tempat pengguna mengetik dan mengirim pesan.
- **Activity 2 (`SecondActivity`)**: menerima pesan dari Activity 1 dan menyediakan kolom untuk menulis balasan.

---

### 2. Menambahkan Komponen di Activity 2
Pada file `activity_second.xml`, tambahkan:
- **`TextView`**: untuk menampilkan pesan yang diterima.
- **`EditText`**: untuk mengetik balasan.
- **`Button`**: untuk mengirim balasan.

**Contoh ID komponen:**
```xml
android:id="@+id/text_message"
android:id="@+id/editTextReply"
android:id="@+id/buttonReply"
```
> **Catatan:** ID pada XML harus sama persis dengan ID yang dipanggil di Java.

---

### 3. Memanggil Komponen di `SecondActivity.java`
Tambahkan kode berikut di dalam method `onCreate()`, setelah `setContentView()`:
```java
TextView textMessage = findViewById(R.id.text_message);
EditText editTextReply = findViewById(R.id.editTextReply);
Button buttonReply = findViewById(R.id.buttonReply);
```
Kode `findViewById()` digunakan untuk menghubungkan komponen XML dengan kode Java.
*Jangan lupa menambahkan import `TextView`, `EditText`, dan `Button`.*

---

### 4. Mengambil Pesan yang Diterima
```java
String message = getIntent().getStringExtra(
    MainActivity.EXTRA_MESSAGE
);
if (message != null) {
    textMessage.setText(message);
}
```
`getIntent()` mengambil Intent yang membuka Activity 2, sedangkan `getStringExtra()` mengambil pesan berdasarkan key `EXTRA_MESSAGE`.

---

### 5. Mengirim Balasan ke Activity 1
Letakkan kode berikut setelah kode untuk mengambil pesan, masih di dalam `onCreate()`:
```java
buttonReply.setOnClickListener(v -> {
    String reply = editTextReply.getText().toString().trim();
    if (reply.isEmpty()) {
        editTextReply.setError("Tulis balasan terlebih dahulu");
        return;
    }
    Intent intent = new Intent(
        SecondActivity.this,
        MainActivity.class
    );
    intent.putExtra(MainActivity.EXTRA_MESSAGE, reply);
    startActivity(intent);
});
```

---

### 6. Alur Pengiriman Pesan
1. Pengguna mengetik pesan di Activity 1.
2. Activity 1 mengirim pesan ke Activity 2 melalui `Intent` dan `putExtra()`.
3. Activity 2 menerima pesan dengan `getStringExtra()`.
4. Pengguna mengetik balasan di Activity 2.
5. Activity 2 mengirim balasan ke Activity 1 menggunakan `Intent` dan `putExtra()`.
6. Activity 1 mengambil dan menampilkan balasan tersebut.

> **Kesimpulan:** Agar Activity 2 bisa mengirim balasan, kita membutuhkan kolom `EditText`, tombol `Button`, `Intent`, `putExtra()`, dan `startActivity()`. Activity 1 juga harus mengambil pesan balasan yang diterima agar dapat menampilkannya.

---

## Codebase Reference & Build Guide

- **App Manifest**: [`app/src/main/AndroidManifest.xml`](app/src/main/AndroidManifest.xml)
- **Main Screen**: [`app/src/main/java/com/nextsent/android/MainActivity.java`](app/src/main/java/com/nextsent/android/MainActivity.java)
- **Second Screen**: [`app/src/main/java/com/nextsent/android/SecondActivity.java`](app/src/main/java/com/nextsent/android/SecondActivity.java)
- **Original PDF Document**: [`docs/Materi_Mobile_6.pdf`](docs/Materi_Mobile_6.pdf)
- **GitHub Repository**: [https://github.com/jasonhukom/NextSentAndroid](https://github.com/jasonhukom/NextSentAndroid)
