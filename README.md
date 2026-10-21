# NextSent Android - Two-Way Messaging Application (Facebook Message Style)

This repository contains the implementation and documentation for **Module 6: Activities & Intents (Materi Mobile 6)** — a two-way messaging application inspired by Facebook Messages built with **Android Java** and **RelativeLayout**.

The original lecture document is attached in [`docs/Materi_Mobile_6.pdf`](docs/Materi_Mobile_6.pdf).

---

## 📌 Module Task Description

> **Task Goal**: Build a simple messaging application (similar to Facebook Messages) with 2 Activities.
> 
> 1. **Activity 1 (`MainActivity`)**:
>    - Uses `RelativeLayout` with an `EditText` and a `Button` aligned at the bottom (`android:layout_alignParentBottom="true"`).
>    - Captures text input from `EditText`, stores it in a `String message`, and passes it to Activity 2 using `intent.putExtra(EXTRA_MESSAGE, message)`.
>    - Launches Activity 2 using `startActivityForResult(intent, TEXT_REQUEST)`.
>    - Receives reply messages returned from Activity 2 via `onActivityResult()` and maintains message history on screen.
> 
> 2. **Activity 2 (`SecondActivity`)**:
>    - Uses `RelativeLayout` with a Header and a `TextView` to display the message received from Activity 1.
>    - Contains an `EditText` and a `REPLY` button at the bottom (`android:layout_alignParentBottom="true"`).
>    - Sends reply data back to Activity 1 using `setResult(RESULT_OK, replyIntent)` and closes with `finish()`.

---

## 🛠️ Step-by-Step Implementation

### 1. First Activity Layout (`res/layout/activity_main.xml`)
Configured using `RelativeLayout` to anchor input controls at the bottom:
```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="16dp">

    <!-- Header -->
    <TextView
        android:id="@+id/textViewHeader"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentTop="true"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="16dp"
        android:text="@string/header_shop_food"
        android:textSize="22sp"
        android:textStyle="bold" />

    <!-- Received Reply Display Area -->
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

    <!-- Center Button for Implicit Intent Example -->
    <Button
        android:id="@+id/buttonWeb"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        android:text="@string/button_open_web" />

    <!-- Bottom Input Controls -->
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:orientation="horizontal">

        <EditText
            android:id="@+id/editTextText"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:layout_marginEnd="8dp"
            android:hint="@string/hint_enter_text"
            android:inputType="text" />

        <Button
            android:id="@+id/button"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/button_send" />
    </LinearLayout>

</RelativeLayout>
```

---

### 2. First Activity Logic (`MainActivity.java`)
Declares public static key, gets user input from `EditText`, passes it as an Extra, and listens for the returned result:
```java
package com.nextsent.android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.nextsent.android.extra.MESSAGE";
    public static final int TEXT_REQUEST = 1;

    private EditText editText;
    private TextView textViewReplyHeader;
    private TextView textViewReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editTextText);
        textViewReplyHeader = findViewById(R.id.textViewReplyHeader);
        textViewReply = findViewById(R.id.textViewReply);

        Button buttonSend = findViewById(R.id.button);
        Button buttonWeb = findViewById(R.id.buttonWeb);

        // Send message to SecondActivity via Explicit Intent with Extra
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editText.getText().toString();
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra(EXTRA_MESSAGE, message);
                startActivityForResult(intent, TEXT_REQUEST);
            }
        });

        // Open Web page via Implicit Intent (intent.setData)
        buttonWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW);
                webIntent.setData(Uri.parse("http://www.google.com"));
                startActivity(webIntent);
            }
        });
    }

    // Handle returned reply data from SecondActivity
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

### 3. Second Activity Layout (`res/layout/activity_second.xml`)
Displays received message and provides input controls for returning a reply:
```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="16dp">

    <!-- Header & Message Display -->
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

    <!-- Bottom Input & Reply Controls -->
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:orientation="horizontal">

        <EditText
            android:id="@+id/editTextReply"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:layout_marginEnd="8dp"
            android:hint="@string/hint_enter_reply"
            android:inputType="text" />

        <Button
            android:id="@+id/buttonReply"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/button_reply" />
    </LinearLayout>

</RelativeLayout>
```

---

### 4. Second Activity Logic (`SecondActivity.java`)
Reads Extra message sent from `MainActivity` and returns reply Extra using `setResult(RESULT_OK, replyIntent)`:
```java
package com.nextsent.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.nextsent.android.extra.REPLY";

    private EditText editTextReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView textViewMessage = findViewById(R.id.textViewMessage);
        editTextReply = findViewById(R.id.editTextReply);
        Button buttonReply = findViewById(R.id.buttonReply);

        // Get message Extra from Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(MainActivity.EXTRA_MESSAGE)) {
            String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
            if (message != null && !message.isEmpty()) {
                textViewMessage.setText(message);
            }
        }

        // Return reply Extra to MainActivity via setResult and finish()
        buttonReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reply = editTextReply.getText().toString();
                Intent replyIntent = new Intent();
                replyIntent.putExtra(EXTRA_REPLY, reply);
                setResult(RESULT_OK, replyIntent);
                finish();
            }
        });
    }
}
```

---

## 📖 Module Reference Summary

| Concept | Explanation | Code / Config |
| :--- | :--- | :--- |
| **Activity** | App component providing a window and UI view hierarchy. | Class extending `AppCompatActivity` |
| **Explicit Intent** | Directly launches a target Activity class. | `new Intent(this, SecondActivity.class)` |
| **Implicit Intent** | Requests system/external apps to open data/action. | `new Intent(Intent.ACTION_VIEW).setData(Uri.parse(...))` |
| **Intent Extras** | Key-value pairs attached to Intent payloads. | `intent.putExtra(KEY, value)` / `getIntent().getStringExtra(KEY)` |
| **Activity Result** | Two-way communication loop returning response. | `startActivityForResult()` ➡️ `setResult(RESULT_OK)` ➡️ `onActivityResult()` |
| **Up Navigation** | Ancestral back navigation in Action Bar. | `android:parentActivityName=".MainActivity"` in Manifest |

---

## 📄 Attached Document

Lecture slides attached in repository: [`docs/Materi_Mobile_6.pdf`](docs/Materi_Mobile_6.pdf)
