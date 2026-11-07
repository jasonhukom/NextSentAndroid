package com.nextsent.android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private TextView settingsAvatar, settingsName, settingsAbout, accountLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settingsAvatar = findViewById(R.id.settingsAvatar);
        settingsName = findViewById(R.id.settingsName);
        settingsAbout = findViewById(R.id.settingsAbout);
        accountLabel = findViewById(R.id.accountLabel);

        findViewById(R.id.settingsProfileRow).setOnClickListener(v -> {
            startActivity(new Intent(SettingsActivity.this, ProfileActivity.class));
        });

        findViewById(R.id.rowSwitchAccountSettings).setOnClickListener(v -> {
            showSwitchAccountDialog();
        });

        syncSettings();
    }

    @Override
    protected void onResume() {
        super.onResume();
        syncSettings();
    }

    private void syncSettings() {
        String acc = DataManager.getInstance().getCurrentAccount();
        String about = DataManager.getInstance().getAccountAbout(acc);

        settingsName.setText(acc);
        settingsAbout.setText(about);
        accountLabel.setText(acc);
        settingsAvatar.setText(getInitials(acc));
    }

    private void showSwitchAccountDialog() {
        String[] options = new String[]{"Jason", "Jason Alt"};
        new AlertDialog.Builder(this)
                .setTitle("Switch Account")
                .setItems(options, (dialog, which) -> {
                    DataManager.getInstance().setCurrentAccount(options[which]);
                    syncSettings();
                })
                .show();
    }

    private String getInitials(String name) {
        if (name == null || name.isEmpty()) return "?";
        String[] parts = name.split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Math.min(parts.length, 2); i++) {
            if (!parts[i].isEmpty()) {
                sb.append(parts[i].charAt(0));
            }
        }
        return sb.toString().toUpperCase();
    }
}
