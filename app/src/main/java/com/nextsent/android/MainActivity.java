package com.nextsent.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.nextsent.android.MESSAGE";
    public static final String EXTRA_CONTACT = "com.nextsent.android.CONTACT";
    public static final int TEXT_REQUEST = 1;

    private TextView textLastMessage;

    private String[] people = {
        "Nikita",
        "Alex Smith",
        "John Doe",
        "Sarah Connor",
        "Food Store"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textLastMessage = findViewById(R.id.textLastMessage);
        ListView listView = findViewById(R.id.listViewPeople);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            this,
            android.R.layout.simple_list_item_1,
            people
        );
        listView.setAdapter(adapter);

        // When a person from the list is clicked, launch SecondActivity (Chat)
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String contactName = people[position];
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra(EXTRA_CONTACT, contactName);
                startActivityForResult(intent, TEXT_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TEXT_REQUEST) {
            if (resultCode == RESULT_OK && data != null) {
                String lastMsg = data.getStringExtra(SecondActivity.EXTRA_REPLY);
                if (lastMsg != null && !lastMsg.isEmpty()) {
                    textLastMessage.setText(getString(R.string.text_last_message) + lastMsg);
                    textLastMessage.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}
