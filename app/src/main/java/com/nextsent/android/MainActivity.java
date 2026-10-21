package com.nextsent.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.nextsent.android.MESSAGE";
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);

        // Menerima balasan dari SecondActivity
        String reply = getIntent().getStringExtra(EXTRA_MESSAGE);

        if (reply != null) {
            editText.setText(reply);
        }
    }

    public void openSecondActivity(View view) {
        Intent intent = new Intent(this, SecondActivity.class);

        String message = editText.getText().toString();

        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
