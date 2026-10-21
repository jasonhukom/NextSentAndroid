package com.nextsent.android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.nextsent.android.extra.MESSAGE";
    public static final int TEXT_REQUEST = 1;

    private EditText editText;
    private TextView textViewReplyHeader;
    private TextView textViewReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editTextText);
        textViewReplyHeader = findViewById(R.id.textViewReplyHeader);
        textViewReply = findViewById(R.id.textViewReply);

        Button buttonSend = findViewById(R.id.button);
        Button buttonWeb = findViewById(R.id.buttonWeb);

        // 1. Explicit Intent with Extras & startActivityForResult
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editText.getText().toString();
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra(EXTRA_MESSAGE, message);
                startActivityForResult(intent, TEXT_REQUEST);
            }
        });

        // 2. Implicit Intent with intent.setData()
        buttonWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW);
                webIntent.setData(Uri.parse("http://www.google.com"));
                startActivity(webIntent);
            }
        });
    }

    // 3. Receive returned reply data from SecondActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TEXT_REQUEST) {
            if (resultCode == RESULT_OK && data != null) {
                String reply = data.getStringExtra(SecondActivity.EXTRA_REPLY);
                textViewReplyHeader.setVisibility(View.VISIBLE);
                textViewReply.setText(reply);
                textViewReply.setVisibility(View.VISIBLE);
            }
        }
    }
}
