package com.nextsent.android;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 42;

    private TextView buttonTopProfile, myStatusAvatar;
    private Button tabChats, tabStatus, tabCalls;
    private LinearLayout searchBar;
    private EditText searchInput;
    private RelativeLayout chatsScreen;
    private ScrollView statusScreen;
    private RecyclerView chatList, callsScreen;
    private FloatingActionButton fabNewGroup;
    private ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonTopProfile = findViewById(R.id.buttonTopProfile);
        myStatusAvatar = findViewById(R.id.myStatusAvatar);
        tabChats = findViewById(R.id.tabChats);
        tabStatus = findViewById(R.id.tabStatus);
        tabCalls = findViewById(R.id.tabCalls);
        searchBar = findViewById(R.id.searchBar);
        searchInput = findViewById(R.id.searchInput);
        chatsScreen = findViewById(R.id.chatsScreen);
        statusScreen = findViewById(R.id.statusScreen);
        chatList = findViewById(R.id.chatList);
        callsScreen = findViewById(R.id.callsScreen);
        fabNewGroup = findViewById(R.id.fabNewGroup);

        ImageButton buttonCamera = findViewById(R.id.buttonCamera);
        ImageButton buttonSearch = findViewById(R.id.buttonSearch);
        ImageButton buttonSettings = findViewById(R.id.buttonSettings);

        buttonTopProfile.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ProfileActivity.class)));
        buttonSettings.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SettingsActivity.class)));

        buttonCamera.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST);
            } catch (Exception e) {
                Toast.makeText(this, "Could not open camera app.", Toast.LENGTH_SHORT).show();
            }
        });

        buttonSearch.setOnClickListener(v -> {
            if (searchBar.getVisibility() == View.VISIBLE) {
                searchBar.setVisibility(View.GONE);
                searchInput.setText("");
            } else {
                searchBar.setVisibility(View.VISIBLE);
                searchInput.requestFocus();
            }
        });

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterChats(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        tabChats.setOnClickListener(v -> showTab("chats"));
        tabStatus.setOnClickListener(v -> showTab("status"));
        tabCalls.setOnClickListener(v -> showTab("calls"));

        chatList.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter(DataManager.getInstance().getContacts(), contact -> {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            intent.putExtra(SecondActivity.EXTRA_CONTACT_NAME, contact.getName());
            startActivity(intent);
        });
        chatList.setAdapter(chatAdapter);

        fabNewGroup.setOnClickListener(v -> showNewGroupDialog());

        syncProfileHeader();
    }

    private void filterChats(String query) {
        query = query.toLowerCase().trim();
        List<Contact> all = DataManager.getInstance().getContacts();
        if (query.isEmpty()) {
            chatAdapter.updateData(all);
            return;
        }

        List<Contact> filtered = new ArrayList<>();
        for (Contact c : all) {
            if (c.getName().toLowerCase().contains(query) || c.getLastMessage().toLowerCase().contains(query)) {
                filtered.add(c);
            }
        }
        chatAdapter.updateData(filtered);
    }

    @Override
    protected void onResume() {
        super.onResume();
        syncProfileHeader();
        if (chatAdapter != null) {
            chatAdapter.notifyDataSetChanged();
        }
    }

    private void syncProfileHeader() {
        String acc = DataManager.getInstance().getCurrentAccount();
        String init = getInitials(acc);
        buttonTopProfile.setText(init);
        myStatusAvatar.setText(init);
    }

    private void showTab(String tab) {
        tabChats.setTextColor(tab.equals("chats") ? 0xFFFFFFFF : 0xCCE5DF);
        tabStatus.setTextColor(tab.equals("status") ? 0xFFFFFFFF : 0xCCE5DF);
        tabCalls.setTextColor(tab.equals("calls") ? 0xFFFFFFFF : 0xCCE5DF);

        chatsScreen.setVisibility(tab.equals("chats") ? View.VISIBLE : View.GONE);
        statusScreen.setVisibility(tab.equals("status") ? View.VISIBLE : View.GONE);
        callsScreen.setVisibility(tab.equals("calls") ? View.VISIBLE : View.GONE);
    }

    private void showNewGroupDialog() {
        final EditText input = new EditText(this);
        input.setHint("Group subject");

        new AlertDialog.Builder(this)
                .setTitle("New Group")
                .setView(input)
                .setPositiveButton("Create", (dialog, which) -> {
                    String groupName = input.getText().toString().trim();
                    if (!groupName.isEmpty()) {
                        DataManager.getInstance().addGroup(groupName, "Alex, Maya, Noah");
                        chatAdapter.notifyDataSetChanged();
                        Toast.makeText(this, "Group created!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
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
