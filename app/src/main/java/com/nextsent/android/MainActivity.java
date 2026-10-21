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
    private TextView textViewMessageHistory;
    private StringBuilder messageHistory = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        textViewMessageHistory = findViewById(R.id.textViewMessageHistory);
    }

    // Called when clicking SEND (android:onClick="openSecondActivity")
    public void openSecondActivity(View view) {
        String message = editText.getText().toString().trim();
        if (!message.isEmpty()) {
            if (messageHistory.length() > 0) {
                messageHistory.append("\n");
            }
            messageHistory.append("Sent: ").append(message);
            textViewMessageHistory.setText(messageHistory.toString());
            textViewMessageHistory.setVisibility(View.VISIBLE);

            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivityForResult(intent, TEXT_REQUEST);

            editText.setText("");
        }
    }

    // Called when clicking OPEN WEBSITE (android:onClick="openWeb")
    public void openWeb(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.google.com"));
        startActivity(intent);
    }

    // Receives returned reply data from SecondActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TEXT_REQUEST && resultCode == RESULT_OK && data != null) {
            String reply = data.getStringExtra(SecondActivity.EXTRA_REPLY);
            if (reply != null && !reply.isEmpty()) {
                if (messageHistory.length() > 0) {
                    messageHistory.append("\n");
                }
                messageHistory.append("Received Reply: ").append(reply);
                textViewMessageHistory.setText(messageHistory.toString());
                textViewMessageHistory.setVisibility(View.VISIBLE);
            }
        }
    }
}
