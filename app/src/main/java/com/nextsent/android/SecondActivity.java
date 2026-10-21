package com.nextsent.android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.nextsent.android.REPLY";

    private EditText editText;
    private TextView textChatHeader;
    private TextView textChatHistory;
    private ScrollView scrollViewChat;
    private String lastSentMessage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textChatHeader = findViewById(R.id.textChatHeader);
        textChatHistory = findViewById(R.id.textChatHistory);
        scrollViewChat = findViewById(R.id.scrollViewChat);
        editText = findViewById(R.id.editText);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(MainActivity.EXTRA_CONTACT)) {
            String contactName = intent.getStringExtra(MainActivity.EXTRA_CONTACT);
            if (contactName != null && !contactName.isEmpty()) {
                textChatHeader.setText("Chat with " + contactName);
                textChatHistory.append("System: Conversation started with " + contactName + "\n");
            }
        }
    }

    // Called when clicking SEND (android:onClick="sendMessage")
    // Appends message on screen in real-time like Facebook Messages!
    public void sendMessage(View view) {
        String message = editText.getText().toString().trim();
        if (!message.isEmpty()) {
            lastSentMessage = message;
            textChatHistory.append("\nYou: " + message);
            editText.setText("");

            // Auto-scroll to bottom
            scrollViewChat.post(new Runnable() {
                @Override
                public void run() {
                    scrollViewChat.fullScroll(View.FOCUS_DOWN);
                }
            });

            // Set result to return last message to MainActivity
            Intent replyIntent = new Intent();
            replyIntent.putExtra(EXTRA_REPLY, lastSentMessage);
            setResult(RESULT_OK, replyIntent);
        }
    }

    // Called when clicking GO TO WEBSITE (android:onClick="openWeb")
    public void openWeb(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.google.com"));
        startActivity(intent);
    }
}
