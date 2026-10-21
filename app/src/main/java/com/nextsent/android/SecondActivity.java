package com.nextsent.android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    private EditText editTextReply;
    private TextView textHistory;
    private String historyText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textHistory = findViewById(R.id.textHistory);
        editTextReply = findViewById(R.id.editTextReply);
        Button buttonReply = findViewById(R.id.buttonReply);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(MainActivity.EXTRA_HISTORY)) {
            String passedHistory = intent.getStringExtra(MainActivity.EXTRA_HISTORY);
            if (passedHistory != null) {
                historyText = passedHistory;
                textHistory.setText(historyText);
            }
        }

        buttonReply.setOnClickListener(v -> {
            String reply = editTextReply.getText().toString().trim();

            if (reply.isEmpty()) {
                Toast.makeText(
                        SecondActivity.this,
                        "Pesan tidak boleh kosong",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            if (!historyText.isEmpty()) {
                historyText += "\n";
            }
            historyText += "SecondActivity: " + reply;
            textHistory.setText(historyText);

            Intent replyIntent = new Intent();
            replyIntent.putExtra(MainActivity.EXTRA_HISTORY, historyText);
            setResult(RESULT_OK, replyIntent);
            finish();
        });
    }
}
