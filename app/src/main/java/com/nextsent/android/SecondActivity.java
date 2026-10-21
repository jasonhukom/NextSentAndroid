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
    private TextView textViewMessageHistory;
    private final StringBuilder messageHistory = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textViewMessageHistory = findViewById(R.id.textViewMessageHistory);
        editTextReply = findViewById(R.id.editTextReply);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(MainActivity.EXTRA_MESSAGE)) {
            String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
            if (message != null && !message.isEmpty()) {
                messageHistory.append("Received Message: ").append(message);
                textViewMessageHistory.setText(messageHistory.toString());
            }
        }
    }

    // Called when clicking REPLY (android:onClick="returnReply")
    public void returnReply(View view) {
        String reply = editTextReply.getText().toString().trim();
        if (!reply.isEmpty()) {
            if (messageHistory.length() > 0) {
                messageHistory.append("\n");
            }
            messageHistory.append("Sent Reply: ").append(reply);
            textViewMessageHistory.setText(messageHistory.toString());

            Intent replyIntent = new Intent();
            replyIntent.putExtra(EXTRA_REPLY, reply);
            setResult(RESULT_OK, replyIntent);
            finish();
        }
    }
}
