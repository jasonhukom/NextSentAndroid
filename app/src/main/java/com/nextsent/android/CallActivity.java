package com.nextsent.android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CallActivity extends AppCompatActivity {

    public static final String EXTRA_CALL_NAME = "com.nextsent.android.CALL_NAME";

    private TextView callName, callState, callAvatar;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable noAnswerRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        callName = findViewById(R.id.callName);
        callState = findViewById(R.id.callState);
        callAvatar = findViewById(R.id.callAvatar);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_CALL_NAME)) {
            String name = intent.getStringExtra(EXTRA_CALL_NAME);
            callName.setText(name);
            callAvatar.setText(getInitials(name));
        }

        ImageButton buttonEndCallHeader = findViewById(R.id.buttonEndCallHeader);
        buttonEndCallHeader.setOnClickListener(v -> finishCall());

        ImageButton buttonEndCall = findViewById(R.id.buttonEndCall);
        buttonEndCall.setOnClickListener(v -> finishCall());

        ImageButton buttonSwitchDuringCall = findViewById(R.id.buttonSwitchDuringCall);
        buttonSwitchDuringCall.setOnClickListener(v -> {
            Toast.makeText(this, "Switching accounts ended call.", Toast.LENGTH_SHORT).show();
            finishCall();
        });

        noAnswerRunnable = () -> callState.setText("No answer");
        handler.postDelayed(noAnswerRunnable, 10000);
    }

    private void finishCall() {
        if (handler != null && noAnswerRunnable != null) {
            handler.removeCallbacks(noAnswerRunnable);
        }
        finish();
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
