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
    private TextView textViewReplyHeader;
    private TextView textViewReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        textViewReplyHeader = findViewById(R.id.textViewReplyHeader);
        textViewReply = findViewById(R.id.textViewReply);
    }

    // Called when clicking the SEND button in activity_main.xml (android:onClick="openSecondActivity")
    public void openSecondActivity(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivityForResult(intent, TEXT_REQUEST);
    }

    // Called when clicking the OPEN WEBSITE button in activity_main.xml (android:onClick="openWeb")
    public void openWeb(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.google.com"));
        startActivity(intent);
    }

    // Receives returned reply data from SecondActivity
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
