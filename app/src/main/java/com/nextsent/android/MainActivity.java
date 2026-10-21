package com.nextsent.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.nextsent.android.MESSAGE";
    public static final String EXTRA_HISTORY = "com.nextsent.android.HISTORY";
    public static final int TEXT_REQUEST = 1;

    private EditText editText;
    private TextView textHistory;
    private String historyText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        textHistory = findViewById(R.id.textHistory);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_HISTORY)) {
            String updatedHistory = intent.getStringExtra(EXTRA_HISTORY);
            if (updatedHistory != null) {
                historyText = updatedHistory;
                textHistory.setText(historyText);
            }
        }
    }

    public void openSecondActivity(View view) {
        String input = editText.getText().toString().trim();
        if (!input.isEmpty()) {
            if (!historyText.isEmpty()) {
                historyText += "\n";
            }
            historyText += "MainActivity: " + input;
            textHistory.setText(historyText);

            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra(EXTRA_MESSAGE, input);
            intent.putExtra(EXTRA_HISTORY, historyText);
            startActivityForResult(intent, TEXT_REQUEST);

            editText.setText("");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TEXT_REQUEST && resultCode == RESULT_OK && data != null) {
            String updatedHistory = data.getStringExtra(EXTRA_HISTORY);
            if (updatedHistory != null && !updatedHistory.isEmpty()) {
                historyText = updatedHistory;
                textHistory.setText(historyText);
            }
        }
    }
}
