package com.nextsent.android;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView profileAvatar, profileName, profileAbout, profileAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileAvatar = findViewById(R.id.profileAvatar);
        profileName = findViewById(R.id.profileName);
        profileAbout = findViewById(R.id.profileAbout);
        profileAccount = findViewById(R.id.profileAccount);

        ImageButton buttonBack = findViewById(R.id.buttonBackFromProfile);
        buttonBack.setOnClickListener(v -> finish());

        findViewById(R.id.rowEditName).setOnClickListener(v -> editField("name"));
        findViewById(R.id.rowEditAbout).setOnClickListener(v -> editField("about"));
        findViewById(R.id.rowSwitchAccount).setOnClickListener(v -> showSwitchAccountDialog());

        syncProfile();
    }

    private void syncProfile() {
        String acc = DataManager.getInstance().getCurrentAccount();
        String about = DataManager.getInstance().getAccountAbout(acc);

        profileName.setText(acc);
        profileAbout.setText(about);
        profileAccount.setText(acc);
        profileAvatar.setText(getInitials(acc));
    }

    private void editField(String which) {
        final EditText input = new EditText(this);
        String currentVal = "name".equals(which) ? DataManager.getInstance().getCurrentAccount() : DataManager.getInstance().getAccountAbout(DataManager.getInstance().getCurrentAccount());
        input.setText(currentVal);

        new AlertDialog.Builder(this)
                .setTitle("Edit " + which)
                .setView(input)
                .setPositiveButton("Save", (dialog, whichButton) -> {
                    String newVal = input.getText().toString().trim();
                    if (!newVal.isEmpty()) {
                        if ("name".equals(which)) {
                            DataManager.getInstance().setCurrentAccount(newVal);
                        } else {
                            DataManager.getInstance().setAccountAbout(DataManager.getInstance().getCurrentAccount(), newVal);
                        }
                        syncProfile();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showSwitchAccountDialog() {
        String[] options = new String[]{"Jason", "Jason Alt", "+ Add Account"};
        new AlertDialog.Builder(this)
                .setTitle("Switch Account")
                .setItems(options, (dialog, which) -> {
                    if (which == 2) {
                        final EditText input = new EditText(this);
                        new AlertDialog.Builder(this)
                                .setTitle("New Account Name")
                                .setView(input)
                                .setPositiveButton("Add", (d, w) -> {
                                    String name = input.getText().toString().trim();
                                    if (!name.isEmpty()) {
                                        DataManager.getInstance().setCurrentAccount(name);
                                        syncProfile();
                                    }
                                })
                                .show();
                    } else {
                        DataManager.getInstance().setCurrentAccount(options[which]);
                        syncProfile();
                    }
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
