package com.nextsent.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class SecondActivity extends AppCompatActivity {

    private EditText editTextReply;
    private TextView textHistory;
    private ImageButton buttonTheme;
    private SwitchCompat switchActivity;
    private String historyText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Apply persistent theme before setContentView
        ThemeManager.applyTheme(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textHistory = findViewById(R.id.textHistory);
        editTextReply = findViewById(R.id.editTextReply);
        Button buttonReply = findViewById(R.id.buttonReply);
        buttonTheme = findViewById(R.id.buttonTheme);
        switchActivity = findViewById(R.id.switchActivity);

        updateThemeIcon();

        // Persistent theme toggle on button click
        buttonTheme.setOnClickListener(v -> {
            boolean isDark = ThemeManager.isDarkMode(SecondActivity.this);
            ThemeManager.setDarkMode(SecondActivity.this, !isDark);
            updateThemeIcon();
        });

        // Switch back to MainActivity when switch is untoggled
        switchActivity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    Intent replyIntent = new Intent();
                    replyIntent.putExtra(MainActivity.EXTRA_HISTORY, historyText);
                    setResult(RESULT_OK, replyIntent);
                    finish();
                }
            }
        });

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(MainActivity.EXTRA_HISTORY)) {
            String passedHistory = intent.getStringExtra(MainActivity.EXTRA_HISTORY);
            if (passedHistory != null) {
                historyText = passedHistory;
                textHistory.setText(historyText);
            }
        }

        // Called when clicking Kirim button
        buttonReply.setOnClickListener(v -> returnReply(v));
    }

    private void updateThemeIcon() {
        if (ThemeManager.isDarkMode(this)) {
            buttonTheme.setImageResource(R.drawable.ic_sun);
        } else {
            buttonTheme.setImageResource(R.drawable.ic_moon);
        }
    }

    // Called when clicking Kirim (android:onClick="returnReply")
    public void returnReply(View view) {
        String reply = editTextReply.getText().toString().trim();

        if (reply.isEmpty()) {
            Toast.makeText(
                    SecondActivity.this,
                    "Pesan tidak boleh kosong",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        if (historyText.length() > 0) {
            historyText += "\n";
        }
        historyText += "SecondActivity: " + reply;
        textHistory.setText(historyText);

        Intent replyIntent = new Intent();
        replyIntent.putExtra(MainActivity.EXTRA_HISTORY, historyText);
        setResult(RESULT_OK, replyIntent);
        finish();
    }
}
