package com.nextsent.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.nextsent.android.MESSAGE";
    public static final String EXTRA_HISTORY = "com.nextsent.android.HISTORY";
    public static final int TEXT_REQUEST = 1;

    private EditText editText;
    private TextView textHistory;
    private ImageButton buttonTheme;
    private SwitchCompat switchActivity;
    private String historyText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Apply persistent theme before setContentView
        ThemeManager.applyTheme(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        textHistory = findViewById(R.id.textHistory);
        buttonTheme = findViewById(R.id.buttonTheme);
        switchActivity = findViewById(R.id.switchActivity);

        updateThemeIcon();

        // Persistent theme toggle on button click
        buttonTheme.setOnClickListener(v -> {
            boolean isDark = ThemeManager.isDarkMode(MainActivity.this);
            ThemeManager.setDarkMode(MainActivity.this, !isDark);
            updateThemeIcon();
        });

        // Switch to SecondActivity when switch is toggled
        switchActivity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    intent.putExtra(EXTRA_HISTORY, historyText);
                    startActivityForResult(intent, TEXT_REQUEST);
                }
            }
        });

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_HISTORY)) {
            String updatedHistory = intent.getStringExtra(EXTRA_HISTORY);
            if (updatedHistory != null) {
                historyText = updatedHistory;
                textHistory.setText(historyText);
            }
        }
    }

    private void updateThemeIcon() {
        if (ThemeManager.isDarkMode(this)) {
            buttonTheme.setImageResource(R.drawable.ic_sun);
        } else {
            buttonTheme.setImageResource(R.drawable.ic_moon);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (switchActivity != null) {
            switchActivity.setChecked(false);
        }
    }

    // Called when clicking SEND (android:onClick="openSecondActivity")
    public void openSecondActivity(View view) {
        String input = editText.getText().toString().trim();
        if (!input.isEmpty()) {
            if (historyText.length() > 0) {
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

    // Receives returned reply data from SecondActivity
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
