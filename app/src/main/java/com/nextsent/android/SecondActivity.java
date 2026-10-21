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

        // Get Intent message extra from MainActivity
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
