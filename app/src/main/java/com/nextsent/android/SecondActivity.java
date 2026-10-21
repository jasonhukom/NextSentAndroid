package com.nextsent.android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView textMessage = findViewById(R.id.text_message);
        EditText editTextReply = findViewById(R.id.editTextReply);
        Button buttonReply = findViewById(R.id.buttonReply);

        // Menampilkan pesan dari Activity 1
        String message = getIntent().getStringExtra(MainActivity.EXTRA_MESSAGE);

        if (message != null) {
            textMessage.setText(message);
        }

        // Mengirim balasan ke Activity 1
        buttonReply.setOnClickListener(v -> {
            String reply = editTextReply.getText().toString().trim();

            if (reply.isEmpty()) {
                Toast.makeText(
                        SecondActivity.this,
                        "Pesan tidak boleh kosong",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            Intent intent = new Intent(SecondActivity.this, MainActivity.class);
            intent.putExtra(MainActivity.EXTRA_MESSAGE, reply);
            startActivity(intent);
        });
    }
}
